package com.sap.cloud.samples.ariba.partner.flow.scheduler;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.partner.flow.api.PartnerFlowExtensionApiFacade;
import com.sap.cloud.samples.ariba.partner.flow.api.UnsuccessfulOperationException;
import com.sap.cloud.samples.ariba.partner.flow.connectivity.IndiaLocalizationDestination;
import com.sap.cloud.samples.ariba.partner.flow.daos.AsnDao;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.AsnDataDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.ContactDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.DataRequestDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.OrderRequestDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.OrderRequestHeaderDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.ShipControlDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.ShipNoticeHeaderDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.ShipNoticeRequestDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.ShipmentIdentifierDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.event.EventDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.event.EventsDto;
import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.Asn;
import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.Po;
import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.PostalAddress;
import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.ShipmentIdentifier;

/**
 * Used to retrieve events from Ariba and save them in database.
 *
 */
public class IndiaLocalizationEventsProcessor {

	private static final String DEBUG_EVENT_WITH_EVENT_ID_ALREADY_EXISTS_WILL_NOT_PROCESS_IT = "Event with event id [{}] already exists. Will not process it.";
	private static final String DEBUG_ACKNOWLEDGING_EVENT_WITH_EVENT_ID = "Acknowledging event with event id [{}]...";
	private static final String DEBUG_ACKNOWLEDGED_EVENT_WITH_EVENT_ID = "Acknowledged event with event id [{}].";
	private static final String DEBUG_STARTED_PROCESSING_EVENTS = "Started processing events...";
	private static final String DEBUG_PROCESSING_EVENTS = "Processing [{}] events...";
	private static final String DEBUG_NO_EVENTS_TO_PROCESS = "No events to process.";
	private static final String DEBUG_EVENT_WITH_EVENT_ID_DOES_NOT_EXIST_STARTING_PROCESSING_IT = "Event with event id [{}] does not exist. Starting processing it...";
	private static final String DEBUG_PROCESSING_EVENT_WITH_EVENT_ID_FINISHED = "Processing event with event id [{}] finished.";
	private static final String DEBUG_PROCESSING_EVENTS_FINISHED = "Processing events finished.";
	private static final String DEBUG_PERSISTING_ASN = "Persisting asn: [{}]...";
	private static final String DEBUG_ASN_IS_PERSISTED = "Asn [{}] is persisted.";
	private static final String DEBUG_RETRIEVING_EVENT_DATA_FOR_EVENT_WITH_EVENT_ID = "Retrieving event data for event with event id [{}]...";
	private static final String DEBUG_RETRIEVED_THE_FOLLOWING_EVENT_DATA_FOR_EVENT_WITH_EVENT_ID = "Retrieved the following event data for event with event id [{}]: [{}]";

	private static final String ERROR_FAILED_TO_PROCESS_EVENT_WITH_EVENT_ID_MESSAGE = "Failed to process event with event id [{0}].";
	private static final String ERROR_PROBLEM_OCUURED_WHILE_PROCESSING_EVENTS = "Problem ocuured while processing events.";

	private static final String DATE_FORMAT_PATTERNT = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String DATE_FORMAT_PATTERNT_REQUESTED_DELIVERY_DATE = "yyyy-MM-dd";

	private static final SimpleDateFormat dateFormatterRequestedDeliveryDate = new SimpleDateFormat(
			DATE_FORMAT_PATTERNT_REQUESTED_DELIVERY_DATE);
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_PATTERNT);

	private static final String SHIP_FROM_CONTACT_ROLE = "shipFrom";

	private AsnDao asnDao;

	private PartnerFlowExtensionApiFacade partnerFlowExtensionFacade;

	private static final Logger logger = LoggerFactory.getLogger(IndiaLocalizationEventsProcessor.class);

	/**
	 * @param indiaLocalizationDestination
	 */
	public IndiaLocalizationEventsProcessor(IndiaLocalizationDestination indiaLocalizationDestination) {
		this.partnerFlowExtensionFacade = new PartnerFlowExtensionApiFacade(
				indiaLocalizationDestination.getAribaOpenApisEnvironmentUrl(),
				indiaLocalizationDestination.getFlowExtensionId(),
				indiaLocalizationDestination.getServiceProviderUser(),
				indiaLocalizationDestination.getServiceProviderPassword(), indiaLocalizationDestination.getApiKey());

		this.asnDao = new AsnDao();
	}

	public void processEvents() throws EventProcessingException {
		logger.debug(DEBUG_STARTED_PROCESSING_EVENTS);

		try {
			EventsDto retrievedEventsDtos = partnerFlowExtensionFacade.retrieveEvents();
			if (retrievedEventsDtos != null && retrievedEventsDtos.getEvents() != null) {
				EventDto[] eventsDtos = retrievedEventsDtos.getEvents();
				logger.debug(DEBUG_PROCESSING_EVENTS, eventsDtos.length);
				for (EventDto eventDto : eventsDtos) {
					String eventId = eventDto.getEventId();
					if (!asnDao.doesExist(eventId)) {
						logger.debug(DEBUG_EVENT_WITH_EVENT_ID_DOES_NOT_EXIST_STARTING_PROCESSING_IT, eventId);
						processEvent(eventDto);
						logger.debug(DEBUG_PROCESSING_EVENT_WITH_EVENT_ID_FINISHED, eventId);
					} else {
						logger.debug(DEBUG_EVENT_WITH_EVENT_ID_ALREADY_EXISTS_WILL_NOT_PROCESS_IT, eventId);
					}
				}
			} else {
				logger.debug(DEBUG_NO_EVENTS_TO_PROCESS);
			}
		} catch (Exception e) {
			logger.error(ERROR_PROBLEM_OCUURED_WHILE_PROCESSING_EVENTS);
			throw new EventProcessingException(ERROR_PROBLEM_OCUURED_WHILE_PROCESSING_EVENTS, e);
		}

		logger.debug(DEBUG_PROCESSING_EVENTS_FINISHED);
	}

	private void processEvent(EventDto eventDto) throws EventProcessingException {
		try {
			String eventId = eventDto.getEventId();

			AsnDataDto asnDataDto = retrieveEventData(eventId);
			persistEventData(asnDataDto);
			acknowledgeEvent(eventId);

		} catch (Exception e) {
			String errorMessage = MessageFormat.format(ERROR_FAILED_TO_PROCESS_EVENT_WITH_EVENT_ID_MESSAGE,
					eventDto.getEventId());
			logger.error(errorMessage);
			throw new EventProcessingException(errorMessage, e);
		}
	}

	private Asn persistEventData(AsnDataDto asnDataDto) throws ParseException {
		logger.debug(DEBUG_PERSISTING_ASN, asnDataDto);

		Asn asn = createAsnEntity(asnDataDto);
		asnDao.create(asn);

		logger.debug(DEBUG_ASN_IS_PERSISTED, asnDataDto);

		return asn;
	}

	private void acknowledgeEvent(String eventId) throws UnsuccessfulOperationException {
		logger.debug(DEBUG_ACKNOWLEDGING_EVENT_WITH_EVENT_ID, eventId);

		partnerFlowExtensionFacade.acknowledgeEvent(eventId);

		logger.debug(DEBUG_ACKNOWLEDGED_EVENT_WITH_EVENT_ID, eventId);
	}

	private AsnDataDto retrieveEventData(String eventId) throws UnsuccessfulOperationException {
		logger.debug(DEBUG_RETRIEVING_EVENT_DATA_FOR_EVENT_WITH_EVENT_ID, eventId);

		AsnDataDto retrieveEventData = partnerFlowExtensionFacade.retrieveEventData(eventId);

		logger.debug(DEBUG_RETRIEVED_THE_FOLLOWING_EVENT_DATA_FOR_EVENT_WITH_EVENT_ID, eventId, retrieveEventData);

		return retrieveEventData;
	}

	private Asn createAsnEntity(AsnDataDto asnData) throws ParseException {
		// create PO
		OrderRequestDto orderRequest = asnData.getDataRequest().getReference().getRequest().getOrderRequest();
		OrderRequestHeaderDto orderRequestHeader = orderRequest.getOrderRequestHeader();
		PostalAddress billToAddressEntity = new PostalAddress(
				orderRequestHeader.getBillTo().getAddress().getPostalAddress());
		String orderDateString = orderRequestHeader.getOrderDate();
		Date orderDate = dateFormatter.parse(orderDateString);
		String orderID = orderRequestHeader.getOrderID();
		String requestedDeliveryDateString = orderRequest.getItemOut().get(0).getRequestedDeliveryDate();
		Date requestedDeliveryDate = null;
		if (requestedDeliveryDateString != null) {
			requestedDeliveryDate = dateFormatterRequestedDeliveryDate.parse(requestedDeliveryDateString);
		}
		PostalAddress shipToAddressEntity = new PostalAddress(
				orderRequestHeader.getShipTo().getAddress().getPostalAddress());
		String type = orderRequestHeader.getType();
		double totalMoneyAmount = orderRequestHeader.getTotal().getMoney().getContent();
		String totalMoneyCurrency = orderRequestHeader.getTotal().getMoney().getCurrency();

		Po po = new Po(orderID, type, orderDate, requestedDeliveryDate, totalMoneyAmount, totalMoneyCurrency,
				billToAddressEntity, shipToAddressEntity);

		// create ASN
		DataRequestDto dataRequest = asnData.getDataRequest();
		String eventId = dataRequest.getEventId();
		ShipNoticeRequestDto shipNoticeRequest = dataRequest.getSource().getRequest().getShipNoticeRequest();
		ShipNoticeHeaderDto shipNoticeHeader = shipNoticeRequest.getShipNoticeHeader();
		String noticeDateString = shipNoticeHeader.getNoticeDate();
		Date noticeDate = dateFormatter.parse(noticeDateString);
		String operation = shipNoticeHeader.getOperation();

		Date shipmentDate = null;
		Date deliveryDate = null;
		if (shipNoticeHeader.getShipmentDate() != null && !shipNoticeHeader.getShipmentDate().isEmpty()) {
			shipmentDate = dateFormatterRequestedDeliveryDate.parse(shipNoticeHeader.getShipmentDate());
		}
		if (shipNoticeHeader.getDeliveryDate() != null && !shipNoticeHeader.getDeliveryDate().isEmpty()) {
			deliveryDate = dateFormatter.parse(shipNoticeHeader.getDeliveryDate());
		}

		String supplierName = null;

		PostalAddress supplierShipControlContactAddressEntity = null;

		List<ContactDto> shipNoticeContacts = shipNoticeRequest.getShipNoticeHeader().getContact();
		for (ContactDto contact : shipNoticeContacts) {
			if (contact.getRole().equals(SHIP_FROM_CONTACT_ROLE)) {
				supplierName = contact.getName().getContent();
				supplierShipControlContactAddressEntity = new PostalAddress(contact.getPostalAddress());
			}
		}

		ShipControlDto shipControl = shipNoticeRequest.getShipControl();
		String routeMethod = null;
		ShipmentIdentifier shipmentIdentifierEntity = null;
		if (shipControl != null) {
			if (shipControl.getRoute() != null) {
				routeMethod = shipControl.getRoute().getMethod();
			}
			ShipmentIdentifierDto shipmentIdentifierDto = shipControl.getShipmentIdentifier();
			shipmentIdentifierEntity = new ShipmentIdentifier(shipmentIdentifierDto);

		}

		Asn asn = new Asn(eventId, supplierName, noticeDate, shipmentDate, operation, deliveryDate,
				supplierShipControlContactAddressEntity, shipmentIdentifierEntity, routeMethod, po);

		return asn;
	}

}

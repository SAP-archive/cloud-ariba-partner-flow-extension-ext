
package com.sap.cloud.samples.ariba.partner.flow.api;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.AsnDataDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.event.EventDto;
import com.sap.cloud.samples.ariba.partner.flow.dtos.event.EventsDto;

/**
 * Facade for the Partner Flow Extension API.
 *
 */
public class PartnerFlowExtensionApiFacade {

	private static final String ERROR_PROBLEM_OCCURED_WHILE_CALLING_URI_MESSAGE = "Problem occured while calling [{0}].";
	private static final String ERROR_DOCUMENT_NAME_IS_NOT_VALID_MESSAGE = "Document name [{0}] is not valid. Document names must be less than 64 characters.";

	private static final String DEBUG_CALLING_URI_MESSAGE = "Calling [{}] ...";
	private static final String DEBUG_CALLING_URI_WITH_PAYLOAD_MESSAGE = "Calling [{}] with payload [{}] ...";
	private static final String DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE = "Calling [{}] returned status: {}";
	private static final String DEBUG_CALLED_URI_SUCCESSFULLY_MESSAGE = "Called [{}] successfully.";

	private static final String GET_EVENTS_PATH = "/flowextensions/{0}/events?count={1}";
	private static final String ACKNOWLEDGE_EVENTS_PATH = "/flowextensions/action/{0}/events/acknowledge";
	private static final String RETRIEVE_EVENT_DATA_PATH = "/flowextensions/{0}/events/{1}/eventdata";
	private static final String POST_DOCUMENT_UPDATE_PATH = "/flowextensions/{0}/events/{1}";
	private static final String RESUME_EVENT_PATH = "/flowextensions/action/{0}/events/{1}/resume";

	private static final int MAXIMUM_NUMBER_EVENTS = 100;
	private static final int DOCUMENT_NAME_MAXIMUM_CHARACTERS = 64;

	private static final String BINARY_PART_NAME_FILE = "file";
	private static final String MEDIA_TYPE_APPLICATION_PDF = "application/pdf";

	private final String acknowledgeEventsPath;
	private final String flowExtensionId;

	private final OpenApisEndpoint openApiEndpoint;

	private static final Logger logger = LoggerFactory.getLogger(PartnerFlowExtensionApiFacade.class);

	/**
	 * Constructor.
	 * 
	 * @param aribaOpenApisEnvironmentUrl
	 *            the URL of the Ariba OpenAPIs environment to be called.
	 * @param flowExtensionId
	 *            the Flow Extension id.
	 * @param serviceProviderUser
	 *            the service provider user.
	 * @param serviceProviderPassword
	 *            the service provider password.
	 * @param apiKey
	 *            API key to be used for the API calls.
	 */
	public PartnerFlowExtensionApiFacade(String aribaOpenApisEnvironmentUrl, String flowExtensionId,
			String serviceProviderUser, String serviceProviderPassword, String apiKey) {
		this.acknowledgeEventsPath = MessageFormat.format(ACKNOWLEDGE_EVENTS_PATH, flowExtensionId);
		this.flowExtensionId = flowExtensionId;
		this.openApiEndpoint = new OpenApisEndpoint(aribaOpenApisEnvironmentUrl, serviceProviderUser,
				serviceProviderPassword, apiKey);
	}

	/**
	 * Retrieves the maximum number {@value #MAXIMUM_NUMBER_EVENTS} of most
	 * recent events from the Extension Queue.
	 * 
	 * @return the {@value #MAXIMUM_NUMBER_EVENTS} most recent events from the
	 *         Extension Queue or null in case of no events.
	 * @throws UnsuccessfulOperationException
	 *             when retrieving the events was not successful.
	 */
	public EventsDto retrieveEvents() throws UnsuccessfulOperationException {
		return retrieveEvents(MAXIMUM_NUMBER_EVENTS);
	}

	/**
	 * Retrieves the "count" most recent events from Extension Queue.
	 * 
	 * @param count
	 *            the number of events to be retrieved from the Extension Queue.
	 * @return the "count" most recent events from the Extension Queue or null
	 *         in case of no events.
	 * @throws UnsuccessfulOperationException
	 *             when retrieving the events was not successful.
	 */
	public EventsDto retrieveEvents(int count) throws UnsuccessfulOperationException {
		EventsDto result = null;

		String retrieveEventsUri = MessageFormat.format(GET_EVENTS_PATH, flowExtensionId, count);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

		logger.debug(DEBUG_CALLING_URI_MESSAGE, retrieveEventsUri);
		try (CloseableHttpResponse retrieveEventsResponse = openApiEndpoint.executeHttpGet(retrieveEventsUri,
				headers)) {
			int retrieveEventsResponseStatusCode = HttpResponseUtils.validateHttpStatusResponse(retrieveEventsResponse,
					HttpStatus.SC_OK, HttpStatus.SC_NO_CONTENT);

			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, retrieveEventsUri,
					retrieveEventsResponseStatusCode);

			if (retrieveEventsResponseStatusCode == HttpStatus.SC_OK) {
				HttpEntity retrieveEventsResponseEntity = retrieveEventsResponse.getEntity();
				if (retrieveEventsResponseEntity != null) {
					try {
						result = HttpResponseUtils.deserialize(retrieveEventsResponseEntity, EventsDto.class);
					} finally {
						EntityUtils.consume(retrieveEventsResponseEntity);
					}
				}
			}
		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_URI_MESSAGE,
					retrieveEventsUri);
			logger.error(errorMessage);
			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_CALLED_URI_SUCCESSFULLY_MESSAGE, retrieveEventsUri);

		return result;
	}

	/**
	 * Acknowledges events. Acknowledged events are not returned in subsequent
	 * Get Event calls. Subsequent API calls for the events, such as Post
	 * Document Update and Resume, will not work until Acknowledge has been
	 * called.
	 * 
	 * @param eventIds
	 *            list of events' ids to be acknowledged.
	 * @throws UnsuccessfulOperationException
	 *             when acknowledge fails.
	 */
	public void acknowledgeEvents(List<String> eventIds) throws UnsuccessfulOperationException {

		String acknowledgeEventsJson = getAcknowledgeEventsJson(eventIds);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

		logger.debug(DEBUG_CALLING_URI_WITH_PAYLOAD_MESSAGE, acknowledgeEventsPath, acknowledgeEventsJson);
		try (CloseableHttpResponse acknowledgeEventsResponse = openApiEndpoint.executeHttpPost(acknowledgeEventsPath,
				headers, acknowledgeEventsJson);) {
			int acknowledgeEventsResponseStatusCode = HttpResponseUtils
					.validateHttpStatusResponse(acknowledgeEventsResponse, HttpStatus.SC_OK);

			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, acknowledgeEventsPath,
					acknowledgeEventsResponseStatusCode);
		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_URI_MESSAGE,
					acknowledgeEventsPath);
			logger.error(errorMessage);
			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_CALLED_URI_SUCCESSFULLY_MESSAGE, acknowledgeEventsPath);
	}

	private String getAcknowledgeEventsJson(List<String> eventIds) {
		EventDto[] eventsArray = new EventDto[eventIds.size()];

		for (int i = 0; i < eventIds.size(); i++) {
			EventDto event = new EventDto();
			event.setEventId(eventIds.get(i));
			eventsArray[i] = event;
		}

		EventsDto events = new EventsDto();
		events.setEvents(eventsArray);

		Gson gson = new Gson();
		return gson.toJson(events);
	}

	/**
	 * Acknowledges event. Acknowledged event is not returned in subsequent Get
	 * Event calls. Subsequent API calls for the event, such as Post Document
	 * Update and Resume, will not work until Acknowledge has been called.
	 * 
	 * @param eventId
	 *            event id to be acknowledged.
	 * @throws UnsuccessfulOperationException
	 *             when acknowledge fails.
	 */
	public void acknowledgeEvent(String eventId) throws UnsuccessfulOperationException {
		List<String> eventIds = new ArrayList<String>();
		eventIds.add(eventId);

		acknowledgeEvents(eventIds);
	}

	/**
	 * Retrieves event data.
	 * 
	 * @param eventId
	 *            event id which data will be retrieved
	 * @return event data.
	 * @throws UnsuccessfulOperationException
	 */
	public AsnDataDto retrieveEventData(String eventId) throws UnsuccessfulOperationException {
		AsnDataDto result = null;

		String retrieveEventDataPath = MessageFormat.format(RETRIEVE_EVENT_DATA_PATH, flowExtensionId, eventId);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

		logger.debug(DEBUG_CALLING_URI_MESSAGE, retrieveEventDataPath);
		try (CloseableHttpResponse retrieveEventDataResponse = openApiEndpoint.executeHttpGet(retrieveEventDataPath,
				headers)) {
			int retrieveEventDataResponseStatusCode = HttpResponseUtils
					.validateHttpStatusResponse(retrieveEventDataResponse, HttpStatus.SC_OK);
			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, retrieveEventDataPath,
					retrieveEventDataResponseStatusCode);

			HttpEntity retrieveEventDataResponseEntity = retrieveEventDataResponse.getEntity();
			if (retrieveEventDataResponseEntity != null) {
				try {
					result = HttpResponseUtils.deserialize(retrieveEventDataResponseEntity, AsnDataDto.class);
				} finally {
					EntityUtils.consume(retrieveEventDataResponseEntity);
				}
			}
		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_URI_MESSAGE,
					retrieveEventDataPath);
			logger.error(errorMessage);
			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_CALLED_URI_SUCCESSFULLY_MESSAGE, retrieveEventDataPath);

		return result;
	}

	/**
	 * Posts attachments to an event.
	 * 
	 * @param eventId
	 *            the id of the event.
	 * @param documents
	 *            map with document names and input streams to be attached. The
	 *            document names must be no more than 64 characters.
	 * @throws UnsuccessfulOperationException
	 *             when the operation is not successful.
	 */
	public void postDocumentUpdate(String eventId, Map<String, InputStream> documents)
			throws UnsuccessfulOperationException {
		validateDocumentNames(documents.keySet());

		String postDocumentUpdatePath = MessageFormat.format(POST_DOCUMENT_UPDATE_PATH, flowExtensionId, eventId);

		List<BinaryBody> binaryBodies = createBinaryBodies(documents);

		logger.debug(DEBUG_CALLING_URI_MESSAGE, postDocumentUpdatePath);
		try (CloseableHttpResponse postDocumentUpdateResponse = openApiEndpoint.executeHttpPost(postDocumentUpdatePath,
				binaryBodies)) {
			int postDocumentUpdateResponseStatusCode = HttpResponseUtils
					.validateHttpStatusResponse(postDocumentUpdateResponse, HttpStatus.SC_OK);

			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, postDocumentUpdatePath,
					postDocumentUpdateResponseStatusCode);
		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_URI_MESSAGE,
					postDocumentUpdatePath);
			logger.error(errorMessage);

			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_CALLED_URI_SUCCESSFULLY_MESSAGE, postDocumentUpdatePath);
	}

	private void validateDocumentNames(Set<String> documentNames) {
		for (String documentName : documentNames) {
			if (!isDocumentNameValid(documentName)) {
				String errorMessage = MessageFormat.format(ERROR_DOCUMENT_NAME_IS_NOT_VALID_MESSAGE, documentName);
				logger.error(errorMessage);
				throw new InvalidParameterException(errorMessage);
			}
		}
	}

	private boolean isDocumentNameValid(String documentName) {
		return documentName.length() <= DOCUMENT_NAME_MAXIMUM_CHARACTERS;
	}

	private List<BinaryBody> createBinaryBodies(Map<String, InputStream> documents) {
		List<BinaryBody> binaryBodies = new ArrayList<BinaryBody>(documents.size());
		for (String documentName : documents.keySet()) {
			binaryBodies.add(new BinaryBody(BINARY_PART_NAME_FILE, MEDIA_TYPE_APPLICATION_PDF, documentName,
					documents.get(documentName)));
		}

		return binaryBodies;
	}

	/**
	 * Completes the Flow Extension and allows the document to continue with its
	 * normal processing flow.
	 * 
	 * @param eventId
	 *            the id of the event.
	 * @throws UnsuccessfulOperationException
	 *             when the resume was not successful.
	 */
	public void resumeEvent(String eventId) throws UnsuccessfulOperationException {
		String resumeEventPath = MessageFormat.format(RESUME_EVENT_PATH, flowExtensionId, eventId);

		logger.debug(DEBUG_CALLING_URI_MESSAGE, resumeEventPath);
		try (CloseableHttpResponse resumeEventResponse = openApiEndpoint.executeHttpPost(resumeEventPath)) {
			int resumeEventResponseStatusCode = HttpResponseUtils.validateHttpStatusResponse(resumeEventResponse,
					HttpStatus.SC_OK);

			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, resumeEventPath, resumeEventResponseStatusCode);
		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_URI_MESSAGE,
					resumeEventPath);
			logger.error(errorMessage);

			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_CALLED_URI_SUCCESSFULLY_MESSAGE, resumeEventPath);
	}

}

package com.sap.cloud.samples.ariba.partner.flow.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.partner.flow.api.PartnerFlowExtensionApiFacade;
import com.sap.cloud.samples.ariba.partner.flow.connectivity.IndiaLocalizationDestination;
import com.sap.cloud.samples.ariba.partner.flow.daos.AsnDao;
import com.sap.cloud.samples.ariba.partner.flow.daos.AttachmentDocumentDao;
import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.Asn;
import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.AttachmentDocument;

@Path("events")
public class EventResource {

	private static final String DEBUG_RETRIEVING_ALL_ASNS = "Retrieving all ASNs...";
	private static final String DEBUG_NO_ASNS_FOUND = "No ASNs found.";
	private static final String DEBUG_ASNS_WERE_FOUND = "[{}] ASNs were found.";
	private static final String DEBUG_RETRIEVING_AS_NS_FOR_EVENT_WITH_EVENT_ID = "Retrieving ASNs for event with event ID [{}]... ";
	private static final String DEBUG_NO_ASN_WITH_EVENT_ID_WAS_FOUND = "No ASN with event id [{}] was found.";
	private static final String DEBUG_FOUND_ASNS_WITH_EVENT_ID = "Found [{}] ASNs with event id [{}].";
	private static final String DEBUG_UPLOADING_ATTACHMENT_DOCUMENTS_FOR_EVENT_WITH_EVENT_ID = "Uploading attachment documents for event with event id [{}]...";
	private static final String DEBUG_EVENT_WITH_EVENT_ID_WAS_NOT_FOUND = "Event with event id [{}] was not found.";
	private static final String DEBUG_UPLOADING_DOCUMENTS_IN_ECM_FOR_EVENT_WITH_EVENT_ID = "Uploading documents in ECM for event with event id [{}]...";
	private static final String DEBUG_RETRIEVING_DOCUMENTS_FROM_ECM = "Retrieving documents from ECM...";
	private static final String DEBUG_POSTING_POCUMENTS_TO_ARIBA_FOR_EVENT_WITH_EVENT_ID = "Posting pocuments to Ariba for event with event id [{}]...";
	private static final String DEBUG_RESUMING_EVENT_WITH_EVENT_ID = "Resuming event with event id [{}]...";
	private static final String DEBUG_UPDATING_DATABASE_FOR_EVENT_WITH_EVENT_ID = "Updating database for event with event id [{}]...";
	private static final String DEBUG_UPLOADING_DOCUMENTS_FOR_EVENT_WITH_EVENT_ID_FINISHED = "Uploading documents for event with event id [{}] finished.";

	private static final String ERROR_OPERATION_WAS_NOT_SUCCESSFUL = "Operation was not successful.";
	private static final String ERROR_PROBLEM_OCCURED_WHILE_DOWNLOADING_DOCUMENT = "Problem occured while downloading document.";

	private static final String RESPONSE_UPLOADING_DOCUMENT_WAS_NOT_SUCCESSFUL = "Uploading document failed. Check the logs for more information.";

	private static final String DOWNLOAD_DOCUMENT_API = "{eventId}/download";
	private static final String UPLOAD_DOCUMENT_API = "{eventId}/upload";
	private static final String RETRIEVE_EVENT_API = "{eventId}";

	private static final String PARAMETER_EVENT_ID = "eventId";
	private static final String QUERY_PARAMETER_PATH = "path";
	private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

	private static final String STATUS_RESUMED = "Resumed";

	private final static Logger logger = LoggerFactory.getLogger(EventResource.class);

	private AsnDao asnDao = new AsnDao();
	private AttachmentDocumentDao documentDao = new AttachmentDocumentDao();

	/**
	 * Retrieves all ASNs from the database.
	 * 
	 * @return HTTP 200 with all ASNs in the database or HTTP 204 when there are
	 *         no ASNs in the database.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveAsns() {
		logger.debug(DEBUG_RETRIEVING_ALL_ASNS);

		Response response = null;

		List<Asn> asn = asnDao.readAll();
		if (asn.isEmpty()) {
			logger.debug(DEBUG_NO_ASNS_FOUND);
			response = Response.status(Response.Status.NO_CONTENT).build();
		} else {
			response = Response.status(Response.Status.OK).entity(asn).build();
			logger.debug(DEBUG_ASNS_WERE_FOUND, asn.size());
		}

		return response;
	}

	/**
	 * Retrieves ASN related to the event with the given event id from the
	 * database.
	 * 
	 * @param eventId
	 *            the event id.
	 * @return the ASN related to the event with the given event id.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(RETRIEVE_EVENT_API)
	public Response retrieveAsn(@PathParam(PARAMETER_EVENT_ID) String eventId) {
		logger.debug(DEBUG_RETRIEVING_AS_NS_FOR_EVENT_WITH_EVENT_ID);

		Response response = null;

		Asn asn = asnDao.findByEvent(eventId);
		if (asn == null) {
			logger.debug(DEBUG_NO_ASN_WITH_EVENT_ID_WAS_FOUND);
			response = Response.status(Response.Status.NOT_FOUND).build();
		} else {
			logger.debug(DEBUG_FOUND_ASNS_WITH_EVENT_ID);
			response = Response.status(Response.Status.OK).entity(asn).build();
		}

		return response;
	}

	/**
	 * Sends the documents to the Ariba and resumes the event. Also uploads the
	 * documents to ECM so that they could be downloaded by the user later.
	 * 
	 * @param eventId
	 * @param body
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(UPLOAD_DOCUMENT_API)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadDocument(@PathParam(PARAMETER_EVENT_ID) String eventId, MultipartBody body) {
		logger.debug(DEBUG_UPLOADING_ATTACHMENT_DOCUMENTS_FOR_EVENT_WITH_EVENT_ID);

		Response response = null;

		IndiaLocalizationDestination indiaLocalizationDestination = new IndiaLocalizationDestination(
				IndiaLocalizationDestination.NAME);
		PartnerFlowExtensionApiFacade flowExtensionApiFacade = new PartnerFlowExtensionApiFacade(
				indiaLocalizationDestination.getAribaOpenApisEnvironmentUrl(),
				indiaLocalizationDestination.getFlowExtensionId(),
				indiaLocalizationDestination.getServiceProviderUser(),
				indiaLocalizationDestination.getServiceProviderPassword(), indiaLocalizationDestination.getApiKey());

		Asn asn = asnDao.findByEvent(eventId);
		if (asn == null) {
			logger.debug(DEBUG_EVENT_WITH_EVENT_ID_WAS_NOT_FOUND);
			response = Response.status(Response.Status.NOT_FOUND).build();
		} else {
			try {
				// upload all attachments in the ECM repository
				logger.debug(DEBUG_UPLOADING_DOCUMENTS_IN_ECM_FOR_EVENT_WITH_EVENT_ID, eventId);
				List<AttachmentDocument> uploadedAttachments = documentDao.addAll(eventId, body.getAllAttachments());

				// post documents to Ariba
				logger.debug(DEBUG_RETRIEVING_DOCUMENTS_FROM_ECM);
				Map<String, InputStream> documents = retrieveDocuments(uploadedAttachments);
				logger.debug(DEBUG_POSTING_POCUMENTS_TO_ARIBA_FOR_EVENT_WITH_EVENT_ID, eventId);
				flowExtensionApiFacade.postDocumentUpdate(eventId, documents);

				// resume event
				logger.debug(DEBUG_RESUMING_EVENT_WITH_EVENT_ID, eventId);
				flowExtensionApiFacade.resumeEvent(eventId);

				// update the ASN entity
				logger.debug(DEBUG_UPDATING_DATABASE_FOR_EVENT_WITH_EVENT_ID, eventId);
				asn.getDocuments().addAll(uploadedAttachments);
				asn.setStatus(STATUS_RESUMED);
				asnDao.update(asn);

				logger.debug(DEBUG_UPLOADING_DOCUMENTS_FOR_EVENT_WITH_EVENT_ID_FINISHED, eventId);
				response = Response.status(Response.Status.OK).entity(asn).build();
			} catch (Exception e) {
				logger.error(ERROR_OPERATION_WAS_NOT_SUCCESSFUL, e);
				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(RESPONSE_UPLOADING_DOCUMENT_WAS_NOT_SUCCESSFUL).build();
			}
		}

		return response;
	}

	private Map<String, InputStream> retrieveDocuments(List<AttachmentDocument> attachmentDocuments) {
		Map<String, InputStream> documents = new HashMap<String, InputStream>();
		for (AttachmentDocument attachmentDocument : attachmentDocuments) {
			Document document = documentDao.findByPath(attachmentDocument.getPath());
			InputStream inputStream = document.getContentStream().getStream();
			documents.put(attachmentDocument.getName(), inputStream);
		}

		return documents;
	}

	@GET
	@Path(DOWNLOAD_DOCUMENT_API)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadDocument(@PathParam(PARAMETER_EVENT_ID) String eventId,
			@QueryParam(QUERY_PARAMETER_PATH) String path) {

		Response response = null;
		try {
			Document document = documentDao.findByPath(path);
			InputStream inputStream = document.getContentStream().getStream();
			File file = new File(document.getName());
			Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			response = Response.status(Response.Status.OK)
					.header(HEADER_CONTENT_DISPOSITION, "attachment; filename=" + file.getName()).entity(file).build();
		} catch (IOException e) {
			logger.error(ERROR_PROBLEM_OCCURED_WHILE_DOWNLOADING_DOCUMENT, e);
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

}

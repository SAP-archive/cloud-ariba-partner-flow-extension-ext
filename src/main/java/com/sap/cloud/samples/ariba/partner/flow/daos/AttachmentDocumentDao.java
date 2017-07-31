package com.sap.cloud.samples.ariba.partner.flow.daos;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.partner.flow.ecm.EcmRepository;
import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.AttachmentDocument;

/**
 * India Localization attachment document DAO. Works with an ECM repository and
 * the database - information about documents in the ECM repository is persisted
 * in the database.
 *
 */
public class AttachmentDocumentDao extends BaseDao<AttachmentDocument> {

	private static final String DEBUG_CREATING_NEW_ECM_SERVICE_DAO = "Creating new ECM Service DAO...";
	private static final String DEBUG_CREATED_ECM_SERVICE_DAO = "Created ECM Service DAO.";
	private static final String DEBUG_CREATING_ATTACHMENT_FOR_EVENT_WITH_EVENT_ID = "Creating attachment for event with event id [{}]...";
	private static final String DEBUG_CREATED_ATTACHMENT_FOR_EVENT_WITH_EVENT_ID = "Created attachment for event with event id [{}].";
	private static final String DEBUG_CREATING_FOLDER = "Creating folder [{}]...";
	private static final String DEBUG_CREATING_DOCUMENT_IN_FOLDER = "Creating document [{}] in folder [{}]...";
	private static final String DEBUG_PERSISTING_DOCUMENT_INFORMATION_FOR_EVENT_WITH_EVENT_ID = "Persisting document information for event with event id [{}]...";

	private static final String ERROR_CONTENT_DISPOSITION_CANNOT_BE_NULL = "Content disposition cannot be null.";
	private static final String ERROR_FILE_NAME_CANNOT_BE_NULL = "File name cannot be null.";

	private static final String FILENAME_PARAMETER = "filename";

	private static final String ECM_REPOSITORY_NAME = "AribaIndiaLocalizationSample";
	private static final String ECM_REPOSITORY_SECRET_KEY = "AribaIndiaLocalizationSample";

	private EcmRepository ecmRepository;

	private static final Logger logger = LoggerFactory.getLogger(AttachmentDocumentDao.class);

	public AttachmentDocumentDao() {
		super(AttachmentDocument.class);

		logger.debug(DEBUG_CREATING_NEW_ECM_SERVICE_DAO);
		ecmRepository = new EcmRepository(ECM_REPOSITORY_NAME, ECM_REPOSITORY_SECRET_KEY);
		logger.debug(DEBUG_CREATED_ECM_SERVICE_DAO);
	}

	/**
	 * Uploads an attachment document in the ECM repository and persists
	 * document information in the database.
	 * 
	 * @param eventId
	 *            the id of the event related to the attachment document.
	 * @param attachment
	 *            the attachment.
	 * @return
	 */
	public AttachmentDocument add(String eventId, Attachment attachment) {
		logger.debug(DEBUG_CREATING_ATTACHMENT_FOR_EVENT_WITH_EVENT_ID, eventId);

		ContentDisposition contentDisposition = attachment.getContentDisposition();
		if (contentDisposition == null) {
			logger.error(ERROR_CONTENT_DISPOSITION_CANNOT_BE_NULL);
			throw new IllegalArgumentException(ERROR_CONTENT_DISPOSITION_CANNOT_BE_NULL);
		}

		String fileName = contentDisposition.getParameter(FILENAME_PARAMETER);
		if (fileName == null) {
			logger.error(ERROR_FILE_NAME_CANNOT_BE_NULL);
			throw new IllegalArgumentException(ERROR_FILE_NAME_CANNOT_BE_NULL);
		}

		InputStream inputStream = attachment.getObject(InputStream.class);
		Folder eventFolder = ecmRepository.retrieveFolder(ecmRepository.getRootFolder().getPath() + eventId);
		if (eventFolder == null) {
			logger.debug(DEBUG_CREATING_FOLDER, eventId);
			eventFolder = ecmRepository.createFolder(eventId, ecmRepository.getRootFolder());
		}
		logger.debug(DEBUG_CREATING_DOCUMENT_IN_FOLDER, fileName, eventId);
		Document ecmDocument = ecmRepository.createDocument(eventFolder, fileName, inputStream);

		logger.debug(DEBUG_PERSISTING_DOCUMENT_INFORMATION_FOR_EVENT_WITH_EVENT_ID, eventId);
		AttachmentDocument result = create(toAttachmentDocument(ecmDocument));

		logger.debug(DEBUG_CREATED_ATTACHMENT_FOR_EVENT_WITH_EVENT_ID, eventId);
		return result;
	}

	/**
	 * Uploads attachment documents in the ECM repository and persists documents
	 * information in the database.
	 * 
	 * @param attachment
	 *            list of the attachments.
	 * @param eventId
	 *            the id of the event related to the attachment document.
	 * @return
	 */
	public List<AttachmentDocument> addAll(String eventId, List<Attachment> attachments) {
		List<AttachmentDocument> documents = new ArrayList<>();
		for (Attachment attachment : attachments) {
			AttachmentDocument document = add(eventId, attachment);
			documents.add(document);
		}
		return documents;
	}

	/**
	 * Finds ECM document by given path.
	 * 
	 * @param path
	 *            the path of the ECM document.
	 * @return
	 */
	public Document findByPath(String path) {
		return ecmRepository.retrieveDocument(path);
	}

	private AttachmentDocument toAttachmentDocument(Document document) {
		AttachmentDocument attachmentDocument = new AttachmentDocument();

		attachmentDocument.setId(document.getId());
		attachmentDocument.setName(document.getName());
		attachmentDocument.setPath(document.getPaths().get(0));
		attachmentDocument.setLastModificationDate(document.getLastModificationDate());
		attachmentDocument.setLastModifiedBy(document.getLastModifiedBy());

		return attachmentDocument;
	}

}

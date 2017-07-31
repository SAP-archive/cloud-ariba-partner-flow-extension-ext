package com.sap.cloud.samples.ariba.partner.flow.ecm;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import com.sap.ecm.api.RepositoryOptions.Visibility;

/**
 * Represents ECM repository.
 *
 */
public class EcmRepository {

	private static final String DEBUG_DOCUMENT_WAS_FOUND = "Document was found [Path: {}].";
	private static final String DEBUG_DOCUMENT_WAS_NOT_FOUND = "Document was not found [Path: {}].";
	private static final String DEBUG_FOLDER_WAS_FOUND = "Folder was found [Path: {}].";
	private static final String DEBUG_NO_FOLDER_FOUND = "No folder [{}] found.";
	private static final String DEBUG_DOCUMENT_CREATED = "Document created [Name: {}].";
	private static final String DEBUG_CREATING_NEW_DOCUMENT = "Creating new document [Name: {}], [Root Folder: {}] ...";
	private static final String DEBUG_FOLDER_CREATED = "Folder created [Name: {}].]";
	private static final String DEBUG_CREATING_NEW_FOLDER = "Creating new folder [Name: {}, Root Folder: {}].";
	private static final String REPOSITORY_CREATED = "Repository created.";
	private static final String DEBUG_CREATING_NEW_REPOSITORY = "Creating new repository [Options: {}]";
	private static final String DEBUG_CONNECTED_TO_REPOSITORY = "Connected to repository [unique name: {}], [secret key: {}].";
	private static final String DEBUG_CONNECTING_TO_ECM_REPOSITORY = "Connecting to ECM repository [{}] ...";
	private static final String ERROR_FILE_ALREADY_EXISTS = "File with name [{0}] already exists.";
	private static final String ERROR_FOLDER_ALREADY_EXISTS = "Folder with name [{0}] already exists.";

	private static final String CMIS_DOCUMENT_TYPE = "cmis:document";
	private static final String CMIS_FOLDER_TYPE = "cmis:folder";

	private static final String MIME_TYPE_TEXT_PLAIN_UTF_8 = "text/plain; charset=UTF-8";

	private Session cmisSession;

	private final static Logger logger = LoggerFactory.getLogger(EcmRepository.class);

	/**
	 * Constructor. Opens an CMIS session with the given repository name and
	 * secret key. If such repository does not exist, then creates one.
	 * 
	 * @param repositoryName
	 *            the name of the ECM repository.
	 * @param repositorySecretKey
	 *            the secret key of the ECM repository.
	 */
	public EcmRepository(String repositoryName, String repositorySecretKey) {
		openCmisSession(repositoryName, repositorySecretKey);
	}

	private void openCmisSession(String repositoryName, String repositorySecretKey) {
		EcmService ecmService = EcmServiceProvider.getEcmService();
		logger.debug(DEBUG_CONNECTING_TO_ECM_REPOSITORY, repositoryName, repositorySecretKey);

		try {
			cmisSession = ecmService.connect(repositoryName, repositorySecretKey);
		} catch (CmisObjectNotFoundException e) {
			RepositoryOptions options = new RepositoryOptions();
			options.setUniqueName(repositoryName);
			options.setRepositoryKey(repositorySecretKey);
			options.setVisibility(Visibility.PROTECTED);

			logger.debug(DEBUG_CREATING_NEW_REPOSITORY, options);
			ecmService.createRepository(options);
			logger.debug(REPOSITORY_CREATED);

			cmisSession = ecmService.connect(repositoryName, repositorySecretKey);
		}

		logger.debug(DEBUG_CONNECTED_TO_REPOSITORY, repositoryName, repositorySecretKey);
	}

	/**
	 * Gets the root folder of the repository.
	 * 
	 * @return the root folder.
	 */
	public Folder getRootFolder() {
		return cmisSession.getRootFolder();
	}

	/**
	 * Creates a new folder with the given name under the given folder.
	 * 
	 * @param name
	 *            the name of the folder.
	 * @param rootFolder
	 *            the root folder.
	 * @return the created folder.
	 */
	public Folder createFolder(String name, Folder folder) {
		logger.debug(DEBUG_CREATING_NEW_FOLDER, name, folder.getName());

		Folder newFolder = null;

		Map<String, String> newFolderProps = new HashMap<String, String>();
		newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, CMIS_FOLDER_TYPE);
		newFolderProps.put(PropertyIds.NAME, name);
		try {
			newFolder = folder.createFolder(newFolderProps);
		} catch (CmisNameConstraintViolationException e) {
			String errorMessage = MessageFormat.format(ERROR_FOLDER_ALREADY_EXISTS, name);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage, e);
		}
		logger.debug(DEBUG_FOLDER_CREATED, name);

		return newFolder;
	}

	/**
	 * Creates a new document with the given name and content under the given
	 * folder.
	 * 
	 * @param name
	 *            the name of the document.
	 * @param rootFolder
	 *            the root folder.
	 * @param content
	 *            the content of the document.
	 * @return the created document.
	 */
	public Document createDocument(Folder rootFolder, String fileName, InputStream inputStream) {
		logger.debug(DEBUG_CREATING_NEW_DOCUMENT, fileName, rootFolder);

		Document document = null;

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT_TYPE);
		properties.put(PropertyIds.NAME, fileName);
		ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, -1,
				MIME_TYPE_TEXT_PLAIN_UTF_8, inputStream);
		try {
			document = rootFolder.createDocument(properties, contentStream, VersioningState.NONE);
		} catch (CmisNameConstraintViolationException e) {
			String errorMessage = MessageFormat.format(ERROR_FILE_ALREADY_EXISTS, fileName);
			logger.error(errorMessage);
			throw new CmisNameConstraintViolationException(errorMessage, e);
		}
		logger.debug(DEBUG_DOCUMENT_CREATED, fileName);

		return document;
	}

	/**
	 * Retrieves the folder with the given path or null.
	 * 
	 * @param path
	 *            the path of the folder.
	 * @return the searched folder.
	 */
	public Folder retrieveFolder(String path) {
		Folder folder = null;

		try {
			CmisObject cmisObject = cmisSession.getObjectByPath(path);
			if (cmisObject instanceof Folder) {
				folder = (Folder) cmisObject;
				logger.debug(DEBUG_FOLDER_WAS_FOUND, path);
			}
		} catch (CmisObjectNotFoundException e) {
			logger.debug(DEBUG_NO_FOLDER_FOUND);
		}

		return folder;
	}

	/**
	 * Retrieves the document with the given path or null.
	 * 
	 * @param path
	 *            the path of the document.
	 * @return the searched document.
	 */
	public Document retrieveDocument(String path) {
		Document document = null;

		try {
			CmisObject cmisObject = cmisSession.getObjectByPath(path);
			if (cmisObject instanceof Document) {
				document = (Document) cmisObject;
				logger.debug(DEBUG_DOCUMENT_WAS_FOUND, path);
			}
		} catch (CmisObjectNotFoundException e) {
			logger.debug(DEBUG_DOCUMENT_WAS_NOT_FOUND, path);
		}

		return document;
	}
}

package com.sap.cloud.samples.ariba.partner.flow.ecm;

import java.text.MessageFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.ecm.api.EcmService;

/**
 * ECM service (document service) provider.
 */
public class EcmServiceProvider {

	private static final String DEBUG_INITIALIZING_ECM_SERVICE = "Initializing ECM Service ...";
	private static final String DEBUG_ECM_SERVICE_INITIALIZED = "ECM Service initialized.";
	private static final String ERROR_LOOKING_UP_THE_ECM_SERVICE_FAILED = "Looking up the ecm service caused an exception. Make sure the resource [{0}] is set up properly.";

	private static final String ECM_SERVICE_NAME = "java:comp/env/EcmService";

	private static final Logger LOGGER = LoggerFactory.getLogger(EcmServiceProvider.class);

	private static EcmService ecmService = null;

	/**
	 * Initializes and returns singleton EcmService (Document Service).
	 *
	 * @return singleton EcmService (Document Service).
	 */
	public static EcmService getEcmService() {
		if (EcmServiceProvider.ecmService == null) {
			synchronized (EcmServiceProvider.class) {
				if (EcmServiceProvider.ecmService == null) {
					EcmServiceProvider.ecmService = initEcmService();
				}
			}
		}

		return EcmServiceProvider.ecmService;
	}

	private static EcmService initEcmService() {
		LOGGER.debug(DEBUG_INITIALIZING_ECM_SERVICE);

		EcmService ecmService;
		try {
			InitialContext initialContext = new InitialContext();
			ecmService = (EcmService) initialContext.lookup(ECM_SERVICE_NAME);
		} catch (NamingException e) {
			String errorMessage = MessageFormat.format(ERROR_LOOKING_UP_THE_ECM_SERVICE_FAILED, ECM_SERVICE_NAME);
			LOGGER.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		LOGGER.debug(DEBUG_ECM_SERVICE_INITIALIZED);
		return ecmService;
	}
}

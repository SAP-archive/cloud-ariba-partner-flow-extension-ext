package com.sap.cloud.samples.ariba.partner.flow.authentication;

import java.text.MessageFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.security.um.user.UserProvider;

/**
 * User authentication provider.
 */
public class AuthenticationProvider {

	private static final String DEBUG_INITIALIZING_USER_PROVIDER = "Initializing user provider...";
	private static final String DEBUG_USER_PROVIDER_INITIALIZED = "User provider initialized.";
	private static final String ERROR_LOOKING_UP_THE_USER_PROVIDER_FAILED = "Looking up the user provider caused an exception. Make sure the resource [{0}] is set up properly.";

	private static final String USER_PROVIDER_NAME = "java:comp/env/user/Provider";

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);

	private static UserProvider userProvider;

	/**
	 * Initializes and returns singleton UserProvider.
	 * 
	 * @return the user provider instance
	 */
	public static UserProvider retrieveUserProvider() {
		if (AuthenticationProvider.userProvider == null) {
			synchronized (AuthenticationProvider.class) {
				if (AuthenticationProvider.userProvider == null) {
					AuthenticationProvider.userProvider = initUserProvider();
				}
			}
		}

		return userProvider;
	}

	private static UserProvider initUserProvider() {
		logger.debug(DEBUG_INITIALIZING_USER_PROVIDER);

		UserProvider userProvider;
		try {
			InitialContext initialContext = new InitialContext();
			userProvider = (UserProvider) initialContext.lookup(USER_PROVIDER_NAME);
		} catch (NamingException e) {
			String errorMessage = MessageFormat.format(ERROR_LOOKING_UP_THE_USER_PROVIDER_FAILED, USER_PROVIDER_NAME);
			logger.error(errorMessage);
			throw new RuntimeException(errorMessage, e);
		}

		logger.debug(DEBUG_USER_PROVIDER_INITIALIZED);
		return userProvider;
	}
}

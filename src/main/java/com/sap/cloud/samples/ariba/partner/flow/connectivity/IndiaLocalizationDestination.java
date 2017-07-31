package com.sap.cloud.samples.ariba.partner.flow.connectivity;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents destination for India Localization application.
 */
public class IndiaLocalizationDestination extends Destination {

	public static final String NAME = "ariba-india-localization";

	private final String FLOW_EXTENSION_ID = "FlowExtensionId";
	private final String SERVICE_PROVIDER_USER = "User";
	private final String SERVICE_PROVIDER_PASSWORD = "Password";
	private final String API_KEY = "ApiKey";
	private final String OPEN_API_ENVIRONMENT_URL = "URL";
	private static final String JOB_INTERVAL_IN_SECONDS = "JobIntervalInSeconds";
	private static final Integer JOB_INTERVAL_IN_SECONDS_DEFAULT_VALUE = 60;

	private static final String ERROR_DESTINATION_PROPERTY_IS_NOT_VALID_INTEGER_SETTING_DEFAULT_VALUE = "Destination property [{0}] is not valid integer. Setting default value: [{1}].";

	private static final Logger logger = LoggerFactory.getLogger(IndiaLocalizationDestination.class);

	/**
	 * Constructor.
	 * 
	 * @param destinationName
	 *            use {@value #NAME}
	 */
	public IndiaLocalizationDestination(String destinationName) {
		super(destinationName);
	}

	public String getAribaOpenApisEnvironmentUrl() {
		return getPropertyValue(OPEN_API_ENVIRONMENT_URL);
	}

	public String getFlowExtensionId() {
		return getPropertyValue(FLOW_EXTENSION_ID);
	}

	public String getServiceProviderUser() {
		return getPropertyValue(SERVICE_PROVIDER_USER);
	}

	public String getServiceProviderPassword() {
		return getPropertyValue(SERVICE_PROVIDER_PASSWORD);
	}

	public String getApiKey() {
		return getPropertyValue(API_KEY);
	}

	public Integer getJobIntervalSeconds() {
		Integer seconds = JOB_INTERVAL_IN_SECONDS_DEFAULT_VALUE;
		try {
			String jobIntervalSeconds = getPropertyValue(JOB_INTERVAL_IN_SECONDS);
			seconds = Integer.valueOf(jobIntervalSeconds);
		} catch (IllegalArgumentException e) {
			String errorMessage = MessageFormat.format(
					ERROR_DESTINATION_PROPERTY_IS_NOT_VALID_INTEGER_SETTING_DEFAULT_VALUE, JOB_INTERVAL_IN_SECONDS,
					JOB_INTERVAL_IN_SECONDS_DEFAULT_VALUE);
			logger.error(errorMessage);
		}

		return seconds;
	}
}

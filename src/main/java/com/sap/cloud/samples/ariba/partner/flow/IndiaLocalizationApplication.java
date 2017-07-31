package com.sap.cloud.samples.ariba.partner.flow;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.sap.cloud.samples.ariba.partner.flow.resources.EventResource;
import com.sap.cloud.samples.ariba.partner.flow.resources.UserResource;

/**
 * Initialize application and its services.
 *
 */
public class IndiaLocalizationApplication extends Application {

	private static final String DEBUG_SERVICE_INITIALIZED = "Service: {} initialized.";

	private Set<Class<?>> services = new HashSet<>();

	private static final Logger logger = LoggerFactory.getLogger(IndiaLocalizationApplication.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		return getServices();
	}

	private Set<Class<?>> getServices() {
		services.add(JacksonJaxbJsonProvider.class);
		logger.debug(DEBUG_SERVICE_INITIALIZED, JacksonJaxbJsonProvider.class.getName());

		services.add(EventResource.class);
		logger.debug(DEBUG_SERVICE_INITIALIZED, EventResource.class.getName());

		services.add(UserResource.class);
		logger.debug(DEBUG_SERVICE_INITIALIZED, UserResource.class.getName());

		return services;
	}
}
package com.sap.cloud.samples.ariba.partner.flow;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.partner.flow.connectivity.IndiaLocalizationDestination;
import com.sap.cloud.samples.ariba.partner.flow.persistency.EntityManagerFactoryProvider;
import com.sap.cloud.samples.ariba.partner.flow.scheduler.EventsScheduler;

public class IndiaLocalizationContextListener implements ServletContextListener {

	private static final String DEBUG_INITIALIZING_APPLICATION = "Initializing application...";
	private static final String DEBUG_APPLICATION_IS_INITIALIZED = "Application is initialized.";
	private static final String DEBUG_RETRIEVING_DESTINATION = "Retrieving [{}] destination...";
	private static final String DEBUG_RETRIEVED_DESTINATION = "Retrieved [{}] destination.";
	private static final String DEBUG_INITIALIZING_SCHEDULER = "Initializing scheduler...";
	private static final String DEBUG_SCHEDULER_INITIALIZED = "Scheduler is initialized.";
	private static final String DEBUG_STOPPING_SCHEDULER = "Stopping scheduler...";
	private static final String DEBUG_SCHEDULER_STOPPED = "Scheduler is stopped.";
	private static final String ERROR_OCCURED_WHILE_INITIALIZING_APPLICATION_MESSAGE = "Error occurred while initializing application: {}";
	private static final String ERROR_OCCURED_WHILE_SHUTTING_DOWN_SCHEDULER_MESSAGE = "Error occurred while shutting down scheduler: {}";

	private static final Logger logger = LoggerFactory.getLogger(IndiaLocalizationContextListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			logger.debug(DEBUG_INITIALIZING_APPLICATION);

			scheduleEventsRetrieving();

			logger.debug(DEBUG_APPLICATION_IS_INITIALIZED);
		} catch (Exception e) {
			logger.error(ERROR_OCCURED_WHILE_INITIALIZING_APPLICATION_MESSAGE, e);
			throw new RuntimeException(e);
		}
	}

	private void scheduleEventsRetrieving() throws SchedulerException {
		logger.debug(DEBUG_RETRIEVING_DESTINATION, IndiaLocalizationDestination.NAME);
		IndiaLocalizationDestination aribaDestination = new IndiaLocalizationDestination(
				IndiaLocalizationDestination.NAME);
		logger.debug(DEBUG_RETRIEVED_DESTINATION, IndiaLocalizationDestination.NAME);

		logger.debug(DEBUG_INITIALIZING_SCHEDULER);
		EventsScheduler eventsScheduler = EventsScheduler.getInstance();
		eventsScheduler.startAndSchedule(aribaDestination.getJobIntervalSeconds());
		logger.debug(DEBUG_SCHEDULER_INITIALIZED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			stopScheduledEventsRetrieving();
		} catch (SchedulerException e) {
			logger.error(ERROR_OCCURED_WHILE_SHUTTING_DOWN_SCHEDULER_MESSAGE, e);
		}
	}

	private void stopScheduledEventsRetrieving() throws SchedulerException {
		logger.debug(DEBUG_STOPPING_SCHEDULER);

		EventsScheduler eventsScheduler = EventsScheduler.getInstance();
		eventsScheduler.stop();
		
		EntityManagerFactoryProvider.closeEntityManagerFactory();

		logger.debug(DEBUG_SCHEDULER_STOPPED);
	}

}

package com.sap.cloud.samples.ariba.partner.flow.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.partner.flow.connectivity.IndiaLocalizationDestination;

/**
 * Job used to retrieve and persist Flow Extension events from Ariba.
 *
 */
@DisallowConcurrentExecution
public class FetchEventsJob implements Job {

	private static final String DEBUG_FETCH_EVENTS_JOB_EXECUTING_MESSAGE = "Executing Fetch Events Job...";
	private static final String DEBUG_FETCH_EVENTS_JOB_EXECUTED_SUCCESSFULLY_MESSAGE = "Fetch Events Job executed successfully.";
	private static final String DEBUG_FETCH_STARTING_MESSAGE = "Started fetching events...";
	private static final String DEBUG_FETCH_DONE_MESSAGE = "Done fetching events.";

	private static final String ERROR_FAILED_TO_FETCH_EVENTS_MESSAGE = "Failed to fetch events.";

	private static final Logger logger = LoggerFactory.getLogger(FetchEventsJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug(DEBUG_FETCH_EVENTS_JOB_EXECUTING_MESSAGE);

		try {
			processEvents(IndiaLocalizationDestination.NAME);
			logger.debug(DEBUG_FETCH_EVENTS_JOB_EXECUTED_SUCCESSFULLY_MESSAGE);
		} catch (Exception e) {
			logger.error(ERROR_FAILED_TO_FETCH_EVENTS_MESSAGE, e);
		}
	}

	private void processEvents(String aribaDestinationName) throws EventProcessingException {
		logger.debug(DEBUG_FETCH_STARTING_MESSAGE);

		IndiaLocalizationEventsProcessor eventsProcessor = new IndiaLocalizationEventsProcessor(
				new IndiaLocalizationDestination(aribaDestinationName));
		eventsProcessor.processEvents();

		logger.debug(DEBUG_FETCH_DONE_MESSAGE);
	}

}

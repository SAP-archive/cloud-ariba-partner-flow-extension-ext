package com.sap.cloud.samples.ariba.partner.flow.scheduler;

public class EventProcessingException extends Exception {

	private static final long serialVersionUID = 3423889368474789811L;

	public EventProcessingException(String message, Exception e) {
		super(message, e);
	}
}
package com.sap.cloud.samples.ariba.partner.flow.api;

/**
 * Exception for unsuccessful operations.
 *
 */
public class UnsuccessfulOperationException extends Exception {

	private static final long serialVersionUID = 4325493259367379684L;

	public UnsuccessfulOperationException(String message, Exception e) {
		super(message, e);
	}
}
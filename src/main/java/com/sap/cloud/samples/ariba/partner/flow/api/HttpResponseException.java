package com.sap.cloud.samples.ariba.partner.flow.api;

import org.apache.http.HttpException;

/**
 * HTTP response exception.
 *
 */
class HttpResponseException extends HttpException {

	private static final long serialVersionUID = 6801244928635636544L;

	HttpResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	HttpResponseException(String message) {
		super(message);
	}

}

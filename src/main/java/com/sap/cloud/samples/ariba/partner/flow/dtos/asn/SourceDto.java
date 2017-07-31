package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class SourceDto {

	@SerializedName("SourceHeader")
	private SourceHeaderDto sourceHeader;

	@SerializedName("Request")
	private SourceRequestDto request;

	public SourceHeaderDto getSourceHeader() {
		return sourceHeader;
	}

	public void setSourceHeader(SourceHeaderDto sourceHeader) {
		this.sourceHeader = sourceHeader;
	}

	public SourceRequestDto getRequest() {
		return request;
	}

	public void setRequest(SourceRequestDto request) {
		this.request = request;
	}

}

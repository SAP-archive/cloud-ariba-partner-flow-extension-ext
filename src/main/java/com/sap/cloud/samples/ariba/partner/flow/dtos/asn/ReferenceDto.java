package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ReferenceDto {

	@SerializedName("ReferencedHeader")
	private ReferencedHeaderDto referencedHeader;

	@SerializedName("Request")
	private ReferenceRequestDto request;

	public ReferencedHeaderDto getReferencedHeader() {
		return referencedHeader;
	}

	public void setReferencedHeader(ReferencedHeaderDto referencedHeader) {
		this.referencedHeader = referencedHeader;
	}

	public ReferenceRequestDto getRequest() {
		return request;
	}

	public void setRequest(ReferenceRequestDto request) {
		this.request = request;
	}

}

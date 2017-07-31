package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class DocumentReferenceDto {

	@SerializedName("payloadID")
	private String payloadID;

	public String getPayloadID() {
		return payloadID;
	}

	public void setPayloadID(String payloadID) {
		this.payloadID = payloadID;
	}

}

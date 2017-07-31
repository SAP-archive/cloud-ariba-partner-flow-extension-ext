package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ShipmentIdentifierDto {

	@SerializedName("trackingURL")
	private String trackingURL;

	@SerializedName("trackingNumberDate")
	private String trackingNumberDate;

	@SerializedName("content")
	private String content;

	public String getTrackingURL() {
		return trackingURL;
	}

	public void setTrackingURL(String trackingURL) {
		this.trackingURL = trackingURL;
	}

	public String getTrackingNumberDate() {
		return trackingNumberDate;
	}

	public void setTrackingNumberDate(String trackingNumberDate) {
		this.trackingNumberDate = trackingNumberDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

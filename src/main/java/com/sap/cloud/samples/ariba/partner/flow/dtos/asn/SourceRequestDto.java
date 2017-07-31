package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class SourceRequestDto {

	@SerializedName("deploymentMode")
	private String deploymentMode;

	@SerializedName("ShipNoticeRequest")
	private ShipNoticeRequestDto shipNoticeRequest;

	public String getDeploymentMode() {
		return deploymentMode;
	}

	public void setDeploymentMode(String deploymentMode) {
		this.deploymentMode = deploymentMode;
	}

	public ShipNoticeRequestDto getShipNoticeRequest() {
		return shipNoticeRequest;
	}

	public void setShipNoticeRequest(ShipNoticeRequestDto shipNoticeRequest) {
		this.shipNoticeRequest = shipNoticeRequest;
	}

}

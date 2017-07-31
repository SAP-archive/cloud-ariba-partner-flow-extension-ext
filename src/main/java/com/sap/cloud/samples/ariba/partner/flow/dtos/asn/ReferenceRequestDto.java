package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ReferenceRequestDto {

	@SerializedName("deploymentMode")
	private String deploymentMode;

	@SerializedName("OrderRequest")
	private OrderRequestDto orderRequest;

	public String getDeploymentMode() {
		return deploymentMode;
	}

	public void setDeploymentMode(String deploymentMode) {
		this.deploymentMode = deploymentMode;
	}

	public OrderRequestDto getOrderRequest() {
		return orderRequest;
	}

	public void setOrderRequest(OrderRequestDto orderRequest) {
		this.orderRequest = orderRequest;
	}

}

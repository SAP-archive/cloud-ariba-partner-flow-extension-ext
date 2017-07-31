package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrderRequestDto {

	@SerializedName("OrderRequestHeader")
	private OrderRequestHeaderDto orderRequestHeader;

	@SerializedName("ItemOut")
	private List<ItemOutDto> itemOut = null;

	public OrderRequestHeaderDto getOrderRequestHeader() {
		return orderRequestHeader;
	}

	public void setOrderRequestHeader(OrderRequestHeaderDto orderRequestHeader) {
		this.orderRequestHeader = orderRequestHeader;
	}

	public List<ItemOutDto> getItemOut() {
		return itemOut;
	}

	public void setItemOut(List<ItemOutDto> itemOut) {
		this.itemOut = itemOut;
	}

}

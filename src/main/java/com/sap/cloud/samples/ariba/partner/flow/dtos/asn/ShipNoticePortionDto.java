package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShipNoticePortionDto {

	@SerializedName("ShipNoticeItem")
	private List<ShipNoticeItemDto> shipNoticeItem = null;

	@SerializedName("OrderReference")
	private OrderReferenceDto orderReference;

	public List<ShipNoticeItemDto> getShipNoticeItem() {
		return shipNoticeItem;
	}

	public void setShipNoticeItem(List<ShipNoticeItemDto> shipNoticeItem) {
		this.shipNoticeItem = shipNoticeItem;
	}

	public OrderReferenceDto getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(OrderReferenceDto orderReference) {
		this.orderReference = orderReference;
	}

}

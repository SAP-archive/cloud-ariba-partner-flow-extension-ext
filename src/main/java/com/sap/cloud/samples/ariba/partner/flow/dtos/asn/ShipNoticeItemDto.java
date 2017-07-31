package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ShipNoticeItemDto {

	@SerializedName("quantity")
	private Integer quantity;

	@SerializedName("UnitOfMeasure")
	private String unitOfMeasure;

	@SerializedName("shipNoticeLineNumber")
	private Integer shipNoticeLineNumber;

	@SerializedName("lineNumber")
	private Integer lineNumber;

	@SerializedName("ItemID")
	private ItemIdDto itemID;

	@SerializedName("ShipNoticeItemDetail")
	private ShipNoticeItemDetailDto shipNoticeItemDetail;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Integer getShipNoticeLineNumber() {
		return shipNoticeLineNumber;
	}

	public void setShipNoticeLineNumber(Integer shipNoticeLineNumber) {
		this.shipNoticeLineNumber = shipNoticeLineNumber;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public ItemIdDto getItemID() {
		return itemID;
	}

	public void setItemID(ItemIdDto itemID) {
		this.itemID = itemID;
	}

	public ShipNoticeItemDetailDto getShipNoticeItemDetail() {
		return shipNoticeItemDetail;
	}

	public void setShipNoticeItemDetail(ShipNoticeItemDetailDto shipNoticeItemDetail) {
		this.shipNoticeItemDetail = shipNoticeItemDetail;
	}

}

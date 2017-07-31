package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ItemOutDto {

	@SerializedName("quantity")
	private Integer quantity;

	@SerializedName("ItemDetail")
	private ItemDetailDto itemDetail;

	@SerializedName("Distribution")
	private DistributionDto distribution;

	@SerializedName("requestedDeliveryDate")
	private String requestedDeliveryDate;

	@SerializedName("lineNumber")
	private Integer lineNumber;

	@SerializedName("ItemID")
	private ItemIdDto itemID;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ItemDetailDto getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetailDto itemDetail) {
		this.itemDetail = itemDetail;
	}

	public DistributionDto getDistribution() {
		return distribution;
	}

	public void setDistribution(DistributionDto distribution) {
		this.distribution = distribution;
	}

	public String getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}

	public void setRequestedDeliveryDate(String requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
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

}

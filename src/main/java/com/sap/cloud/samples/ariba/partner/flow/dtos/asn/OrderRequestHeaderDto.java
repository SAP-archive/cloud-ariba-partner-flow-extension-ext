package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class OrderRequestHeaderDto {

	@SerializedName("orderType")
	private String orderType;

	@SerializedName("orderID")
	private String orderID;

	@SerializedName("Comments")
	private String comments;

	@SerializedName("Total")
	private TotalDto total;

	@SerializedName("BillTo")
	private BillToDto billTo;

	@SerializedName("ShipTo")
	private ShipToDto shipTo;

	@SerializedName("orderVersion")
	private Integer orderVersion;

	@SerializedName("type")
	private String type;

	@SerializedName("orderDate")
	private String orderDate;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public TotalDto getTotal() {
		return total;
	}

	public void setTotal(TotalDto total) {
		this.total = total;
	}

	public BillToDto getBillTo() {
		return billTo;
	}

	public void setBillTo(BillToDto billTo) {
		this.billTo = billTo;
	}

	public ShipToDto getShipTo() {
		return shipTo;
	}

	public void setShipTo(ShipToDto shipTo) {
		this.shipTo = shipTo;
	}

	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

}

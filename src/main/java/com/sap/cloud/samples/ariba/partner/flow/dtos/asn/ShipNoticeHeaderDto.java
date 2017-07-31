package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShipNoticeHeaderDto {

	@SerializedName("ServiceLevel")
	private ServiceLevelDto serviceLevel;

	@SerializedName("Extrinsic")
	private ExtrinsicDto extrinsic;

	@SerializedName("noticeDate")
	private String noticeDate;

	@SerializedName("shipmentID")
	private String shipmentID;

	@SerializedName("Comments")
	private CommentsDto comments;

	@SerializedName("shipmentDate")
	private String shipmentDate;

	@SerializedName("deliveryDate")
	private String deliveryDate;

	@SerializedName("operation")
	private String operation;

	@SerializedName("shipmentType")
	private String shipmentType;

	@SerializedName("Contact")
	private List<ContactDto> contact = null;

	public ServiceLevelDto getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(ServiceLevelDto serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public ExtrinsicDto getExtrinsic() {
		return extrinsic;
	}

	public void setExtrinsic(ExtrinsicDto extrinsic) {
		this.extrinsic = extrinsic;
	}

	public String getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getShipmentID() {
		return shipmentID;
	}

	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}

	public CommentsDto getComments() {
		return comments;
	}

	public void setComments(CommentsDto comments) {
		this.comments = comments;
	}

	public String getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	public List<ContactDto> getContact() {
		return contact;
	}

	public void setContact(List<ContactDto> contact) {
		this.contact = contact;
	}

}

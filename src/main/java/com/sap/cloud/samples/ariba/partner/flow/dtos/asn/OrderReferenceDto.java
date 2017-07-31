package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class OrderReferenceDto {

	@SerializedName("orderID")
	private String orderID;

	@SerializedName("orderDate")
	private String orderDate;

	@SerializedName("DocumentReference")
	private DocumentReferenceDto documentReference;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public DocumentReferenceDto getDocumentReference() {
		return documentReference;
	}

	public void setDocumentReference(DocumentReferenceDto documentReference) {
		this.documentReference = documentReference;
	}

}

package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ItemIdDto {

	@SerializedName("SupplierPartID")
	private String supplierPartID;

	@SerializedName("SupplierPartAuxiliaryID")
	private Integer supplierPartAuxiliaryID;

	public String getSupplierPartID() {
		return supplierPartID;
	}

	public void setSupplierPartID(String supplierPartID) {
		this.supplierPartID = supplierPartID;
	}

	public Integer getSupplierPartAuxiliaryID() {
		return supplierPartAuxiliaryID;
	}

	public void setSupplierPartAuxiliaryID(Integer supplierPartAuxiliaryID) {
		this.supplierPartAuxiliaryID = supplierPartAuxiliaryID;
	}

}

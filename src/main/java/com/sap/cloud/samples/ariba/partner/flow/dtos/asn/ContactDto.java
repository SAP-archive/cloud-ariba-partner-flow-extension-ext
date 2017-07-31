package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ContactDto {

	@SerializedName("PostalAddress")
	private PostalAddressDto postalAddress;

	@SerializedName("role")
	private String role;

	@SerializedName("Name")
	private NameDto name;

	public PostalAddressDto getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(PostalAddressDto postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public NameDto getName() {
		return name;
	}

	public void setName(NameDto name) {
		this.name = name;
	}

}

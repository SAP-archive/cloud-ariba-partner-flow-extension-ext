package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PostalAddressDto {

	@SerializedName("State")
	private String state;

	@SerializedName("Street")
	private String street;

	@SerializedName("PostalCode")
	private Integer postalCode;

	@SerializedName("Country")
	private CountryDto country;

	@SerializedName("City")
	private String city;

	@SerializedName("DeliverTo")
	private List<String> deliverTo = null;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getDeliverTo() {
		return deliverTo;
	}

	public void setDeliverTo(List<String> deliverTo) {
		this.deliverTo = deliverTo;
	}

}

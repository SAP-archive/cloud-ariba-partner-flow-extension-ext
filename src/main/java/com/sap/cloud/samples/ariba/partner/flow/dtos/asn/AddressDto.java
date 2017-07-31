package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class AddressDto {

    @SerializedName("PostalAddress")
    private PostalAddressDto postalAddress;
    
    @SerializedName("Email")
    private EmailDto email;
    
    @SerializedName("isoCountryCode")
    private String isoCountryCode;
    
    private String addressID;

    @SerializedName("Name")
    private NameDto name;

	public PostalAddressDto getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddressDto postalAddress) {
        this.postalAddress = postalAddress;
    }

    public EmailDto getEmail() {
        return email;
    }

    public void setEmail(EmailDto email) {
        this.email = email;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public NameDto getName() {
        return name;
    }

    public void setName(NameDto name) {
        this.name = name;
    }

}

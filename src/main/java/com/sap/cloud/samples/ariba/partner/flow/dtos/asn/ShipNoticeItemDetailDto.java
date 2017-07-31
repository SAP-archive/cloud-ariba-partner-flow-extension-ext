package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ShipNoticeItemDetailDto {

    @SerializedName("UnitPrice")
    private UnitPriceDto unitPrice;

    @SerializedName("Description")
    private DescriptionDto description;

    @SerializedName("UnitOfMeasure")
    private String unitOfMeasure;

    public UnitPriceDto getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(UnitPriceDto unitPrice) {
        this.unitPrice = unitPrice;
    }

    public DescriptionDto getDescription() {
        return description;
    }

	public void setDescription(DescriptionDto description) {
        this.description = description;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

}

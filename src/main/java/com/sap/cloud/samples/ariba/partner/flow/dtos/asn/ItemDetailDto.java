package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ItemDetailDto {

	@SerializedName("UnitPrice")
	private UnitPriceDto unitPrice;

	@SerializedName("Extrinsic")
	private List<ExtrinsicDto> extrinsic = null;

	@SerializedName("Description")
	private DescriptionDto description;

	@SerializedName("UnitOfMeasure")
	private String unitOfMeasure;

	@SerializedName("LeadTime")
	private Integer leadTime;

	@SerializedName("Classification")
	private ClassificationDto classification;

	@SerializedName("URL")
	private UrlDto uRL;

	public UnitPriceDto getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(UnitPriceDto unitPrice) {
		this.unitPrice = unitPrice;
	}

	public List<ExtrinsicDto> getExtrinsic() {
		return extrinsic;
	}

	public void setExtrinsic(List<ExtrinsicDto> extrinsic) {
		this.extrinsic = extrinsic;
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

	public Integer getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(Integer leadTime) {
		this.leadTime = leadTime;
	}

	public ClassificationDto getClassification() {
		return classification;
	}

	public void setClassification(ClassificationDto classification) {
		this.classification = classification;
	}

	public UrlDto getURL() {
		return uRL;
	}

	public void setURL(UrlDto uRL) {
		this.uRL = uRL;
	}

}

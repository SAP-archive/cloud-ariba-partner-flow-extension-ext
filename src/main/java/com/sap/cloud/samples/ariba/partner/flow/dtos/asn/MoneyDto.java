package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class MoneyDto {

	@SerializedName("alternateAmount")
	private String alternateAmount;

	@SerializedName("currency")
	private String currency;

	@SerializedName("alternateCurrency")
	private String alternateCurrency;

	@SerializedName("content")
	private Double content;

	public String getAlternateAmount() {
		return alternateAmount;
	}

	public void setAlternateAmount(String alternateAmount) {
		this.alternateAmount = alternateAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAlternateCurrency() {
		return alternateCurrency;
	}

	public void setAlternateCurrency(String alternateCurrency) {
		this.alternateCurrency = alternateCurrency;
	}

	public Double getContent() {
		return content;
	}

	public void setContent(Double content) {
		this.content = content;
	}

}

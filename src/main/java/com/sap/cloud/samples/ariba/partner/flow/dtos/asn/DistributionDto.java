package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class DistributionDto {

	@SerializedName("Accounting")
	private AccountingDto accounting;

	@SerializedName("Charge")
	private ChargeDto charge;

	public AccountingDto getAccounting() {
		return accounting;
	}

	public void setAccounting(AccountingDto accounting) {
		this.accounting = accounting;
	}

	public ChargeDto getCharge() {
		return charge;
	}

	public void setCharge(ChargeDto charge) {
		this.charge = charge;
	}

}

package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ChargeDto {

	@SerializedName("Money")
	private MoneyDto money;

	public MoneyDto getMoney() {
		return money;
	}

	public void setMoney(MoneyDto money) {
		this.money = money;
	}

}

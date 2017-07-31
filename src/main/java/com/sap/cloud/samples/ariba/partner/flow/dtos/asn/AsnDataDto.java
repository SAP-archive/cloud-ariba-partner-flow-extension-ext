package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class AsnDataDto {

	@SerializedName("DataRequest")
	private DataRequestDto dataRequest;

	public DataRequestDto getDataRequest() {
		return dataRequest;
	}

	public void setDataRequest(DataRequestDto dataRequest) {
		this.dataRequest = dataRequest;
	}

}

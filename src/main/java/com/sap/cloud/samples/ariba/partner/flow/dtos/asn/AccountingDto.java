package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AccountingDto {

	@SerializedName("name")
	private String name;

	@SerializedName("Segment")
	private List<SegmentDto> segment = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SegmentDto> getSegment() {
		return segment;
	}

	public void setSegment(List<SegmentDto> segment) {
		this.segment = segment;
	}

}

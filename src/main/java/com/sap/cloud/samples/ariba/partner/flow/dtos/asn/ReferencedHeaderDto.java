package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ReferencedHeaderDto {

	@SerializedName("ToANId")
	private String toANId;

	@SerializedName("PayloadId")
	private String payloadId;

	@SerializedName("FromANId")
	private String fromANId;

	@SerializedName("Version")
	private String version;

	@SerializedName("SystemId")
	private String systemId;

	@SerializedName("Timestamp")
	private String timestamp;

	public String getToANId() {
		return toANId;
	}

	public void setToANId(String toANId) {
		this.toANId = toANId;
	}

	public String getPayloadId() {
		return payloadId;
	}

	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}

	public String getFromANId() {
		return fromANId;
	}

	public void setFromANId(String fromANId) {
		this.fromANId = fromANId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}

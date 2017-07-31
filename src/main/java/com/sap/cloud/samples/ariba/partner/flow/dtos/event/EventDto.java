package com.sap.cloud.samples.ariba.partner.flow.dtos.event;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

public class EventDto {

	@SerializedName("eventId")
	private String eventId;

	@SerializedName("created")
	private String created;

	@SerializedName("status")
	private String status;

	@SerializedName("numEventId")
	private BigInteger numEventId;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigInteger getNumEventId() {
		return numEventId;
	}

	public void setNumEventId(BigInteger numEventId) {
		this.numEventId = numEventId;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", created=" + created + ", status=" + status + ", numEventId="
				+ numEventId + "]";
	}

}
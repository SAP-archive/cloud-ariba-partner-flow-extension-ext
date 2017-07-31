package com.sap.cloud.samples.ariba.partner.flow.dtos.event;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

public class EventsDto {

	@SerializedName("flowExtensionId")
	private String flowExtensionId;

	@SerializedName("events")
	private EventDto[] events;

	public String getFlowExtensionId() {
		return flowExtensionId;
	}

	public void setFlowExtensionId(String flowExtensionId) {
		this.flowExtensionId = flowExtensionId;
	}

	public EventDto[] getEvents() {
		return events;
	}

	public void setEvents(EventDto[] events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "Events [flowExtensionId=" + flowExtensionId + ", events=" + Arrays.toString(events) + "]";
	}
}
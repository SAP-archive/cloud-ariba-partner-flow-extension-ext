package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class DataRequestDto {

	@SerializedName("eventId")
	private String eventId;

	@SerializedName("extension")
	private String extension;

	@SerializedName("Reference")
	private ReferenceDto reference;

	@SerializedName("Source")
	private SourceDto source;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public ReferenceDto getReference() {
		return reference;
	}

	public void setReference(ReferenceDto reference) {
		this.reference = reference;
	}

	public SourceDto getSource() {
		return source;
	}

	public void setSource(SourceDto source) {
		this.source = source;
	}

}

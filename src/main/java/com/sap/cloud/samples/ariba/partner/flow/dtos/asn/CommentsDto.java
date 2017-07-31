package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class CommentsDto {

	@SerializedName("xml:lang")
	private String xmlLang;

	@SerializedName("Attachment")
	private AttachmentDto attachment;

	public String getXmlLang() {
		return xmlLang;
	}

	public void setXmlLang(String xmlLang) {
		this.xmlLang = xmlLang;
	}

	public AttachmentDto getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDto attachment) {
		this.attachment = attachment;
	}

}

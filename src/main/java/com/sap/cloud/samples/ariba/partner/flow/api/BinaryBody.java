package com.sap.cloud.samples.ariba.partner.flow.api;

import java.io.InputStream;

/**
 * Represents binary body.
 *
 */
class BinaryBody {

	private String binaryBodyName;
	private String mediaType;
	private String fileName;
	private InputStream fileStream;

	BinaryBody(String binaryBodyName, String mediaType, String fileName, InputStream fileStream) {
		this.binaryBodyName = binaryBodyName;
		this.mediaType = mediaType;
		this.fileName = fileName;
		this.fileStream = fileStream;
	}

	public String getBinaryBodyName() {
		return binaryBodyName;
	}

	public void setBinaryBodyName(String binaryBodyName) {
		this.binaryBodyName = binaryBodyName;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
}

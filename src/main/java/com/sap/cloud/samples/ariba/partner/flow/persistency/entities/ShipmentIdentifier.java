package com.sap.cloud.samples.ariba.partner.flow.persistency.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.ShipmentIdentifierDto;

/**
 * Shipment identifier entity.
 *
 */
@Entity
@XmlRootElement
@Table(name = "IND_LOC_SHIPMENT_IDENTIFIER")
public class ShipmentIdentifier implements Serializable {

	private static final long serialVersionUID = 8641561184943510637L;

	@Id
	@GeneratedValue
	private String id;

	@Column(name = "TRACKING_URL")
	private String trackingURL;

	@Column
	private String identifier;

	@Column(name = "TRACKING_NUMBER_DATE")
	private String trackingNumberDate;

	public ShipmentIdentifier() {
	}

	public ShipmentIdentifier(ShipmentIdentifierDto shipmentIdentifierDto) {
		this.trackingURL = shipmentIdentifierDto.getTrackingURL();
		this.identifier = shipmentIdentifierDto.getContent();
		this.trackingNumberDate = shipmentIdentifierDto.getTrackingNumberDate();
	}

	public String getTrackingURL() {
		return trackingURL;
	}

	public String getTrackingNumberDate() {
		return trackingNumberDate;
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		return "ShipmentIdentifierEntity [id=" + id + ", trackingURL=" + trackingURL + ", identifier=" + identifier
				+ ", trackingNumberDate=" + trackingNumberDate + "]";
	}

}
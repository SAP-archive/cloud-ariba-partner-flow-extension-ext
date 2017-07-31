package com.sap.cloud.samples.ariba.partner.flow.persistency.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.CountryDto;

/**
 * Country code entity.
 *
 */
@Entity
@XmlRootElement
@Table(name = "IND_LOC_COUNTRY_CODE")
public class CountryCode implements Serializable {

	private static final long serialVersionUID = -416789052938449491L;

	@Id
	@GeneratedValue
	private String id;

	@Column
	private String content;

	@Column(name = "ISO_COUNTRY_CODE")
	private String isoCountryCode;

	public CountryCode() {
	}

	public CountryCode(CountryDto contry) {
		this.content = contry.getContent();
		this.isoCountryCode = contry.getIsoCountryCode();
	}

	public String getContent() {
		return content;
	}

	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	@Override
	public String toString() {
		return "CountryCode [id=" + id + ", content=" + content + ", isoCountryCode=" + isoCountryCode + "]";
	}

}

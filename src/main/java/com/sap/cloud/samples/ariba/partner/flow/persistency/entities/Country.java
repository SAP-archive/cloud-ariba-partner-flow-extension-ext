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
 * Country entity.
 *
 */
@Entity
@XmlRootElement
@Table(name = "IND_LOC_COUNTRY")
public class Country implements Serializable {

	private static final long serialVersionUID = -4943059429690951895L;

	@Id
	@GeneratedValue
	private String id;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "ISO_COUNTRY_CODE")
	private String isoCountryCode;

	public Country() {
	}

	public Country(CountryDto countryDto) {
		this.countryName = countryDto.getContent();
		this.isoCountryCode = countryDto.getIsoCountryCode();
	}

	public String getCountryName() {
		return countryName;
	}

	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", countryName=" + countryName + ", isoCountryCode=" + isoCountryCode + "]";
	}

}

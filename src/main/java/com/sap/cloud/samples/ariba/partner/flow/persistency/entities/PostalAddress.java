package com.sap.cloud.samples.ariba.partner.flow.persistency.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.sap.cloud.samples.ariba.partner.flow.dtos.asn.PostalAddressDto;

/**
 * Postal address entity.
 *
 */
@Entity
@XmlRootElement
@Table(name = "IND_LOC_POSTAL_ADDRESS")
public class PostalAddress implements Serializable {

	private static final long serialVersionUID = 3288423739001763750L;

	@Id
	@GeneratedValue
	private String id;

	@Column
	private String street;

	@Column(name = "POSTAL_CODE")
	private long postalCode;

	@Column
	private String state;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_COUNTRY", nullable = false)
	private Country countryEntity;

	@Column
	private String city;

	@ElementCollection
	@CollectionTable(name = "IND_LOC_DELIVER_TO", joinColumns = @JoinColumn(name = "POSTAL_ADDRESS_ID"))
	private List<String> deliverTo;

	public PostalAddress() {
	}

	public PostalAddress(PostalAddressDto postalAddressDto) {
		this.city = postalAddressDto.getCity();
		if (postalAddressDto.getDeliverTo() != null) {
			this.deliverTo = postalAddressDto.getDeliverTo();
		}
		this.countryEntity = new Country(postalAddressDto.getCountry());
		this.postalCode = postalAddressDto.getPostalCode();
		this.state = postalAddressDto.getState();
		this.street = postalAddressDto.getStreet();
	}

	@Override
	public String toString() {
		return "PostalAddressEntity [id=" + id + ", street=" + street + ", postalCode=" + postalCode + ", state="
				+ state + ", countryEntity=" + countryEntity + ", city=" + city + ", deliverTo=" + deliverTo + "]";
	}

	public String getStreet() {
		return street;
	}

	public long getPostalCode() {
		return postalCode;
	}

	public String getState() {
		return state;
	}

	public Country getCountryEntity() {
		return countryEntity;
	}

	public String getCity() {
		return city;
	}

	public List<String> getDeliverTo() {
		return deliverTo;
	}

}

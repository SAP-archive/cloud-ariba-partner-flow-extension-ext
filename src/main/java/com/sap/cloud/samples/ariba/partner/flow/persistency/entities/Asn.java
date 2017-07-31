package com.sap.cloud.samples.ariba.partner.flow.persistency.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Advanced Ship Notice entity.
 *
 */
@Entity
@XmlRootElement
@NamedQueries({ @NamedQuery(name = Asn.QUERY_FIND_BY_EVENT_ID, query = "SELECT a FROM Asn a WHERE a.eventId = :"
		+ Asn.QUERY_PARAM_FIND_BY_EVENT_ID) })
@Table(name = "IND_LOC_ASN")
public class Asn implements Serializable {

	private static final long serialVersionUID = 4295864379064256221L;

	public static final String QUERY_PARAM_FIND_BY_EVENT_ID = "eventId";
	public static final String QUERY_FIND_BY_EVENT_ID = "findByEvent";

	@Id
	@Column(name = "EVENT_ID")
	private String eventId;

	@Column(name = "SUPPLIER_NAME")
	private String supplierName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NOTICE_DATE")
	private Date noticeDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SHIPMENT_DATE")
	private Date shipmentDate;

	@Column
	private String operation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELIVERY_DATE")
	private Date deliveryDate;

	@Column(name = "STATUS")
	private String status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_SHIP_CONTROL_CONTACT_ADDRESS")
	private PostalAddress shipControlContactAddressEntity;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_SHIPMENT_IDENTIFIER")
	private ShipmentIdentifier shipmentIdentifierEntity;

	@Column(name = "ROUTE_METHOD")
	private String routeMethod;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PO", nullable = false)
	private Po poEntity;

	@OneToMany(cascade = CascadeType.ALL)
	private List<AttachmentDocument> documents;

	public Asn() {
	}

	public Asn(String eventId, String supplierName, Date noticeDate, Date shipmentDate, String operation,
			Date deliveryDate, PostalAddress shipControlContactAddressEntity,
			ShipmentIdentifier shipmentIdentifierEntity, String routeMethod, Po poEntity) {
		this.eventId = eventId;
		this.supplierName = supplierName;
		this.noticeDate = noticeDate;
		this.shipmentDate = shipmentDate;
		this.operation = operation;
		this.deliveryDate = deliveryDate;
		this.shipControlContactAddressEntity = shipControlContactAddressEntity;
		this.shipmentIdentifierEntity = shipmentIdentifierEntity;
		this.routeMethod = routeMethod;
		this.poEntity = poEntity;
		this.status = "Created";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AttachmentDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<AttachmentDocument> documents) {
		this.documents = documents;
	}

	public String getEventId() {
		return eventId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public Date getShipmentDate() {
		return shipmentDate;
	}

	public String getOperation() {
		return operation;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public PostalAddress getShipControlContactAddressEntity() {
		return shipControlContactAddressEntity;
	}

	public ShipmentIdentifier getShipmentIdentifierEntity() {
		return shipmentIdentifierEntity;
	}

	public String getRouteMethod() {
		return routeMethod;
	}

	public Po getPoEntity() {
		return poEntity;
	}

	@Override
	public String toString() {
		return "AsnEntity [eventId=" + eventId + ", supplierName=" + supplierName + ", noticeDate=" + noticeDate
				+ ", shipmentDate=" + shipmentDate + ", operation=" + operation + ", deliveryDate=" + deliveryDate
				+ ", shipControlContactAddressEntity=" + shipControlContactAddressEntity + ", shipmentIdentifierEntity="
				+ shipmentIdentifierEntity + ", routeMethod=" + routeMethod + ", poEntity=" + poEntity + ", documents="
				+ documents + "]";
	}

}
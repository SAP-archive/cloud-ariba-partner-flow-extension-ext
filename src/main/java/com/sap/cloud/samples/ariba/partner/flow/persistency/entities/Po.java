package com.sap.cloud.samples.ariba.partner.flow.persistency.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Purchase Order entity.
 *
 */
@Entity
@XmlRootElement
@Table(name = "IND_LOC_PO")
public class Po implements Serializable {

	private static final long serialVersionUID = 5325256323029421110L;

	@Id
	@GeneratedValue
	private String id;

	@Column(name = "PO_ID")
	private String poId;

	@Column(name = "TYPE")
	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_DATE")
	private Date orderDate;

	@Column(name = "MONEY_AMOUNT")
	private double moneyAmount;

	@Column(name = "MONEY_CURRENCY")
	private String moneyCurrency;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQUESTED_DELIVERY_DATE")
	private Date requestedDeliveryDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_BILL_TO_ADDRESS", nullable = false)
	private PostalAddress billToAddressEntity;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_SHIP_TO_ADDRESS", nullable = false)
	private PostalAddress shipToAddressEntity;

	public Po() {
	}

	public Po(String poId, String type, Date orderDate, Date requestedDeliveryDate, double moneyAmount,
			String moneyCurrency, PostalAddress billToAddressEntity, PostalAddress shipToAddressEntity) {
		this.poId = poId;
		this.type = type;
		this.orderDate = orderDate;
		this.requestedDeliveryDate = requestedDeliveryDate;
		this.moneyAmount = moneyAmount;
		this.moneyCurrency = moneyCurrency;
		this.billToAddressEntity = billToAddressEntity;
		this.shipToAddressEntity = shipToAddressEntity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPoId() {
		return poId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public double getMoneyAmount() {
		return moneyAmount;
	}

	public String getMoneyCurrency() {
		return moneyCurrency;
	}

	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}

	public PostalAddress getBillToAddressEntity() {
		return billToAddressEntity;
	}

	public PostalAddress getShipToAddressEntity() {
		return shipToAddressEntity;
	}

	@Override
	public String toString() {
		return "PoEntity [id=" + id + ", poId=" + poId + ", type=" + type + ", orderDate=" + orderDate
				+ ", moneyAmount=" + moneyAmount + ", moneyCurrency=" + moneyCurrency + ", requestedDeliveryDate="
				+ requestedDeliveryDate + ", billToAddressEntity=" + billToAddressEntity + ", shipToAddressEntity="
				+ shipToAddressEntity + "]";
	}

}

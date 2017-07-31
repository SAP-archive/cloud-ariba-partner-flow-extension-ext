package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ShipControlDto {

	@SerializedName("ShipmentIdentifier")
	private ShipmentIdentifierDto shipmentIdentifier;

	@SerializedName("CarrierIdentifier")
	private CarrierIdentifierDto carrierIdentifier;

	@SerializedName("Route")
	private RouteDto route;

	public ShipmentIdentifierDto getShipmentIdentifier() {
		return shipmentIdentifier;
	}

	public void setShipmentIdentifier(ShipmentIdentifierDto shipmentIdentifier) {
		this.shipmentIdentifier = shipmentIdentifier;
	}

	public CarrierIdentifierDto getCarrierIdentifier() {
		return carrierIdentifier;
	}

	public void setCarrierIdentifier(CarrierIdentifierDto carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}

	public RouteDto getRoute() {
		return route;
	}

	public void setRoute(RouteDto route) {
		this.route = route;
	}

}

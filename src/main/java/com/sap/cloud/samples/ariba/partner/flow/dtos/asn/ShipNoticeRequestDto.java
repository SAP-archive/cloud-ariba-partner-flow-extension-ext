package com.sap.cloud.samples.ariba.partner.flow.dtos.asn;

import com.google.gson.annotations.SerializedName;

public class ShipNoticeRequestDto {

	@SerializedName("ShipControl")
	private ShipControlDto shipControl;

	@SerializedName("ShipNoticePortion")
	private ShipNoticePortionDto shipNoticePortion;

	@SerializedName("ShipNoticeHeader")
	private ShipNoticeHeaderDto shipNoticeHeader;

	public ShipControlDto getShipControl() {
		return shipControl;
	}

	public void setShipControl(ShipControlDto shipControl) {
		this.shipControl = shipControl;
	}

	public ShipNoticePortionDto getShipNoticePortion() {
		return shipNoticePortion;
	}

	public void setShipNoticePortion(ShipNoticePortionDto shipNoticePortion) {
		this.shipNoticePortion = shipNoticePortion;
	}

	public ShipNoticeHeaderDto getShipNoticeHeader() {
		return shipNoticeHeader;
	}

	public void setShipNoticeHeader(ShipNoticeHeaderDto shipNoticeHeader) {
		this.shipNoticeHeader = shipNoticeHeader;
	}

}

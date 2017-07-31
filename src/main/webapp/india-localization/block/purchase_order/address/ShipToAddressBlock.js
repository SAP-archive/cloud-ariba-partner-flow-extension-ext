sap.ui.define(['sap/uxap/BlockBase'], function (BlockBase) {
	"use strict";
 
	var ShipToAddressBlock = BlockBase.extend("com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.address.ShipToAddressBlock", {
		metadata: {
			views: {
				Collapsed: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.address.ShipToAddressBlock",
					type: "XML"
				},
				Expanded: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.address.ShipToAddressBlock",
					type: "XML"
				}
			}
		}
	});
 
	return ShipToAddressBlock;
});
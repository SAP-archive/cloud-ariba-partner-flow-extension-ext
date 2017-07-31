sap.ui.define(['sap/uxap/BlockBase'], function (BlockBase) {
	"use strict";
 
	var ShipControl = BlockBase.extend("com.sap.cloud.samples.ariba.partner.flow.block.ship_notice.ShipControl", {
		metadata: {
			views: {
				Collapsed: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.ship_notice.ShipControl",
					type: "XML"
				},
				Expanded: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.ship_notice.ShipControl",
					type: "XML"
				}
			}
		}
	});
 
	return ShipControl;
});
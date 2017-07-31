sap.ui.define(['sap/uxap/BlockBase'], function (BlockBase) {
	"use strict";
 
	var ShipNoticeDetails = BlockBase.extend("com.sap.cloud.samples.ariba.partner.flow.block.ship_notice.ShipNoticeDetails", {
		metadata: {
			views: {
				Collapsed: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.ship_notice.ShipNoticeDetails",
					type: "XML"
				},
				Expanded: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.ship_notice.ShipNoticeDetails",
					type: "XML"
				}
			}
		}
	});
 
	return ShipNoticeDetails;
});
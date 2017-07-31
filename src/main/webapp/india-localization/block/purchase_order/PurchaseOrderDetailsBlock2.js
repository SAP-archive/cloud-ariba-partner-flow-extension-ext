sap.ui.define(['sap/uxap/BlockBase'], function (BlockBase) {
	"use strict";
 
	var PurchaseOrderDetailsBlock2 = BlockBase.extend("com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.PurchaseOrderDetailsBlock2", {
		metadata: {
			views: {
				Collapsed: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.PurchaseOrderDetailsBlock2",
					type: "XML"
				},
				Expanded: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.PurchaseOrderDetailsBlock2",
					type: "XML"
				}
			}
		}
	});
 
	return PurchaseOrderDetailsBlock2;
});
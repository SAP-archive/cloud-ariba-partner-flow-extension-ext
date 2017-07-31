sap.ui.define(['sap/uxap/BlockBase'], function (BlockBase) {
	"use strict";
 
	var PurchaseOrderDetailsBlock1 = BlockBase.extend("com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.PurchaseOrderDetailsBlock1", {
		metadata: {
			views: {
				Collapsed: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.PurchaseOrderDetailsBlock1",
					type: "XML"
				},
				Expanded: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.PurchaseOrderDetailsBlock1",
					type: "XML"
				}
			}
		}
	});
 
	return PurchaseOrderDetailsBlock1;
});
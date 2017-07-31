sap.ui.define(['sap/uxap/BlockBase'], function (BlockBase) {
	"use strict";
 
	var BillToAddressBlock = BlockBase.extend("com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.address.BillToAddressBlock", {
		metadata: {
			views: {
				Collapsed: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.address.BillToAddressBlock",
					type: "XML"
				},
				Expanded: {
					viewName: "com.sap.cloud.samples.ariba.partner.flow.block.purchase_order.address.BillToAddressBlock",
					type: "XML"
				}
			}
		}
	});
 
	return BillToAddressBlock;
});
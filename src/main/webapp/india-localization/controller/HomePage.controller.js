sap.ui.define([ "sap/ui/core/UIComponent", "sap/ui/core/mvc/Controller", 
"sap/ui/model/json/JSONModel", "com/sap/cloud/samples/ariba/partner/flow/service/UserService",
"sap/m/MessageBox"],
	function(UIComponent, Controller, JSONModel, UserService, MessageBox) {
	"use strict";

	return Controller.extend("com.sap.cloud.samples.ariba.partner.flow.controller.HomePage", {
		
		onInit : function() {
			
		},
		
		handleActionSheetPress : function (oEvent) {
			var oButton = oEvent.getSource();
			if (!this._actionSheet) {
				this._actionSheet = sap.ui.xmlfragment("com.sap.cloud.ariba.india.localization.fragment.ActionSheet",this);
				this.getView().addDependent(this._actionSheet);
			}
			this._actionSheet.openBy(oButton);
		},
		handleLogoutUser : function (oEvent) {
			UserService.logoutUser();
		},
	});
});

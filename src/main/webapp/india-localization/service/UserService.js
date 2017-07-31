sap.ui.define("com/sap/cloud/samples/ariba/partner/flow/service/UserService", [
		"com/sap/cloud/samples/ariba/partner/flow/dao/UserDAO",
		"sap/ui/model/json/JSONModel", "sap/m/MessageToast" ], function(
		UserDAO, JSONModel, MessageToast) {
	"use strict";
	return {
		retrieveUserData : function() {
			var oModel = new JSONModel();
			UserDAO.retrieveUserData().success(function(oData) {
				oModel.setData(oData);
			}).fail(function() {
				MessageToast.show("Operation failed.");
			});
			return oModel;
		},
		logoutUser : function() {
			UserDAO.logoutUser().success(function() {
				window.location.reload();
			}).fail(function() {
				MessageToast.show("Logout failed.");
			});
		}
	}
});
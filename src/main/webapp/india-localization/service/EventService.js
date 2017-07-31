sap.ui.define("com/sap/cloud/samples/ariba/partner/flow/service/EventService",
		[ "com/sap/cloud/samples/ariba/partner/flow/dao/EventDAO",
				"sap/ui/core/BusyIndicator", "sap/m/MessageToast",
				"sap/ui/model/json/JSONModel" ], function(EventDAO,
				BusyIndicator, MessageToast, JSONModel) {
			"use strict";

			return {

				retrieveEvents : function() {
					var oModel = new JSONModel();
					BusyIndicator.show();
					EventDAO.retrieveEvents().success(function(oData) {
						oModel.setData(oData);
					}).fail(function(oData) {
						MessageToast.show(oData.responseJSON.errorMessage);
					}).complete(function() {
						BusyIndicator.hide();
					});
					return oModel;
				},

				retrieveEventData : function(eventId) {
					var oModel = new JSONModel();
					BusyIndicator.show();
					EventDAO.retrieveEventData(eventId).success(
							function(oData) {
								oModel.setData(oData);
							}).fail(function(oData) {
						MessageToast.show(oData.responseJSON.errorMessage);
					}).complete(function() {
						BusyIndicator.hide();
					});
					return oModel;
				},

				uploadDocument : function(eventId, formData) {
					var oModel = this.getView().getModel();
					BusyIndicator.show();
					EventDAO.uploadDocument(eventId, formData).success(
							function(oData) {
								var documents = oModel.getProperty("/documents");
								documents.concat(oData.documents);
								oModel.setProperty("/status", oData.status);
								
								MessageToast.show("File uploaded successfuly.")
							}).fail(function(oData) {
								MessageToast.show(oData.responseText);
							}).complete(function() {
								oModel.refresh();
								BusyIndicator.hide();
							});
					return oModel;
				}
			}
		});
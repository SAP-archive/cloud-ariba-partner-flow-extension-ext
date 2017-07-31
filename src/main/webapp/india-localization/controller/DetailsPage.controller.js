sap.ui.define(["sap/ui/core/UIComponent",
    "sap/ui/core/mvc/Controller", "sap/ui/model/json/JSONModel", 
    "com/sap/cloud/samples/ariba/partner/flow/service/EventService",
    "sap/m/MessageBox"
], function (UIComponent, Controller, JSONModel, EventService, MessageBox) {
    "use strict";

    return Controller.extend("com.sap.cloud.samples.ariba.partner.flow.controller.DetailsPage", {

        onInit: function() {
        	this.files = [];
        	 var oRouter = UIComponent.getRouterFor(this);
             oRouter.getRoute("DetailsPage").attachPatternMatched(function (oEvent) {
                 var eventId = oEvent.getParameter("arguments").eventId;
                 this._setEventDataModel(eventId);
             }, this);
        },

        _setEventDataModel: function(eventId) {
        	 this.getView().setModel(EventService.retrieveEventData(eventId));
		},
		
		onUploadPress : function(oEvent) {
			var items = this.getView().byId("UploadCollection").getItems();
			var eventId = this.getView().getModel().getProperty("/eventId");
			var formData = new FormData();
			var files = this.getView().getModel().getProperty("/files");
			for(var index in files) {
				if(this._contains(items, files[index])) {
					formData.append("files[]", files[index], files[index].name);
				}
				
			}
			EventService.uploadDocument.apply(this, [eventId, formData]);
		},
		
		_contains: function(items, file) {
			for(var index in items) {
				if(items[index].getProperty("fileName") === file.name) {
					return true;
				}
			}
			return false;
		},
		
		onChange: function(oEvent) {
			var file = oEvent.getParameter("files") && oEvent.getParameter("files")[0];
			var files = this.getView().getModel().getProperty("/files");
			if(files) {
				files.push(file);
			} else {
				this.getView().getModel().setProperty("/files", []);
				this.getView().getModel().getProperty("/files").push(file);
			}
			
		},
		
		onFileDeleted: function(oEvent) {
			console.log(oEvent);
		},
		
		onTypeMissmatch: function(oEvent) {
			 var aFileTypes = oEvent.getSource().getFileType();
	            jQuery.each(aFileTypes, function (key, value) {
	                aFileTypes[key] = "*." + value
	            });
	            var sSupportedFileTypes = aFileTypes.join(", ");
	            MessageBox.error("The file type *."+oEvent.getParameter("files")[0].fileType+" is not supported. Choose one of the following types: " + sSupportedFileTypes);
		}
    });
});

sap.ui.define(["sap/ui/core/UIComponent", "sap/ui/core/mvc/Controller",
          "sap/ui/model/json/JSONModel", "com/sap/cloud/samples/ariba/partner/flow/service/EventService", 
          "sap/ui/model/Filter", "sap/ui/model/FilterOperator"
], function (UIComponent, Controller, JSONModel, EventService, Filter, FilterOperator) {
    "use strict";

    return Controller.extend("com.sap.cloud.samples.ariba.partner.flow.controller.MasterPage", {

        onInit: function() {
           this._setEventsModel();
        },

        _setEventsModel: function() {
             this.getView().setModel(EventService.retrieveEvents());
		},
		
        handleEventPress: function (oEvent) {
        	  var path = oEvent.getSource().getSelectedItem().getBindingContext().sPath;
        	  var event = oEvent.getSource().getModel().getProperty(path);
              var oRouter = UIComponent.getRouterFor(this);
              oRouter.navTo("DetailsPage", { eventId: event.eventId });
        },
		
		onRefreshPress : function(oEvent) {
			this.getView().setModel(EventService.retrieveEvents());
		},
		
		handleSearch: function (oEvent) {
	        var oSearchString = oEvent.getSource().getValue();
	        var supplierNameFiter = new Filter("supplierName", FilterOperator.StartsWith, oSearchString);
	        var poIdFilter = new Filter("poEntity/poId", FilterOperator.StartsWith, oSearchString);
	        var oFilters = [supplierNameFiter,poIdFilter];
	        var oList = this.getView().byId("itemsList");
	        var oBinding = oList.getBinding("items");
	        oBinding.filter(new Filter(oFilters, false));
	    },
	    
	    handleUpdateFinished: function (oEvent) {
	    	var oList = this.getView().byId("itemsList");
	    	var oItems = oList.getItems();
	    	if(oItems.length !== 0) {
	    		var firstItem = oList.getItems()[0];
		    	oList.setSelectedItem(firstItem, true);
		    	var eventId = firstItem.getModel().getProperty("/0/eventId");
	    		var oRouter = UIComponent.getRouterFor(this);
	            oRouter.navTo("DetailsPage", { eventId: eventId });
	    	} else {
	    		var oRouter = UIComponent.getRouterFor(this);
	            oRouter.navTo("NotFound");
	    	}
	    }
    });
});

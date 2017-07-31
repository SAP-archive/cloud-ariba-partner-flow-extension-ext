sap.ui.define(["sap/ui/core/UIComponent", "com/sap/cloud/samples/ariba/partner/flow/service/UserService"
], function(UIComponent, UserService) {
    "use strict";

    return UIComponent.extend("com.sap.cloud.samples.ariba.partner.flow.Component", {

        metadata: {
            manifest: "json"
        },

        init: function() {
            UIComponent.prototype.init.apply(this, arguments);
            this.getRouter().initialize();
            this._initUserModel();
        },
        
        _initUserModel: function() {
        	this.setModel(UserService.retrieveUserData(), "currentUser");
        }
    });
});

sap.ui.define("com/sap/cloud/samples/ariba/partner/flow/dao/UserDAO", [
], function () {
    "use strict";

    const USER_API = "/api/v1/user/";

    return {

        retrieveUserData: function () {
            return $.ajax({
                type: "GET",
                url: USER_API
            });
        },
        
        logoutUser: function () {
            return $.ajax({
                type: "POST",
                url: USER_API + "logout",
            });
        }
    }
});
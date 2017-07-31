sap.ui.define("com/sap/cloud/samples/ariba/partner/flow/dao/EventDAO", [
], function () {
    "use strict";

    const EVENTS_API = "/api/v1/events/";

    return {

        retrieveEvents: function () {
            return $.ajax({
                type: "GET",
                url: EVENTS_API
            });
        },

        retrieveEventData: function (eventId) {
            return $.ajax({
                type: "GET",
                url: EVENTS_API + eventId
            });
        },

        uploadDocument: function (eventId, formData) {
            return $.ajax({
                type: "POST",
                url: EVENTS_API + eventId + '/upload',
                data: formData,
                processData: false,
                contentType: false
            });
        }
    }
});
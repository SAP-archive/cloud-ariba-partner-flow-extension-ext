[![Not Maintained](https://img.shields.io/badge/Maintenance%20Level-Not%20Maintained-yellow.svg)](https://gist.github.com/cheerfulstoic/d107229326a01ff0f333a1d3476e068d)

# SAP Ariba India Localization
[![REUSE status](https://api.reuse.software/badge/github.com/SAP-samples/cloud-ariba-partner-flow-extension-ext)](https://api.reuse.software/info/github.com/SAP-samples/cloud-ariba-partner-flow-extension-ext)

## Content:

- [Overview](#overview)
- [Technical Details](#technical_details)
- [Prerequisites](#prerequisites)
- [Register a Partner Flow Extension Application](#setup_ariba)
- [Build and Deploy the Application on SAP Cloud Platform](#build_deploy)
	- [Using the SAP Cloud Platform Cockpit](#build_deploy_cockpit) 
	- [Using the Eclipse IDE](#build_deploy_eclipse)
- [Configure The Application](#configure)
- [Start the India Localization Application](#start)
- [Additional Information](#additional_information)
	- [Resources](#additional_information_resources)
	- [License](#additional_information_license)

<a name="overview"/>

## Overview

India Localization is a sample extension application for [SAP Ariba](https://www.ariba.com/) that runs on [SAP Cloud Platform](https://hcp.sap.com/). The purpose of the application is to allow a buyer to attach documents to Advance Ship Notices (ASNs).

In India (and in other countries such as Brasil as well), whenever goods travel from the supplier to the buyer across different states, the supplier needs a waybill document. The waybill document is issued to the buyer by the government upon request. The buyer have to send the document to the supplier in some way, so that the supplier can give it to the truck drivers responsible for delivering the goods.
This is where the India Localization application comes in use. The extension application intercepts the creation of cross-state Advance Ship Notices in SAP Ariba and displays information about them to the buyer. The buyer can request waybill documents from the government and attach them to the ASNs using the extension application.
As a result, the waybill documents are available as an ASN attachment for both the buyer and supplier.

The application uses the Partner Flow Extension API. You can run it either on enterprise, or trial [SAP Cloud Platform account](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/8ed4a705efa0431b910056c0acdbf377.html) in the [Neo environment](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/1a8ee4e7b27d4293af175f021db8ad9c.html).

These are the SAP Cloud Platform services and features in use:
* [Connectivity Service](https://help.hana.ondemand.com/help/frameset.htm?e54cc8fbbb571014beb5caaf6aa31280.html) - the application uses the Connectivity Service to obtain connection to SAP Ariba Open API.
* [SAP HANA / SAP ASE Service](https://help.sap.com/viewer/d4790b2de2f4429db6f3dff54e4d7b3a/Cloud/en-US/f6567e3b7334403b9b275426fbe4fb04.html).
* [Identity Service](https://help.hana.ondemand.com/cloud_identity/frameset.htm) - the application uses the Identity Service to manage its security.
* [Document Service](https://help.sap.com/viewer/b0cc1109d03c4dc299c215871eed8c42/Cloud/en-US) - the application uses the Document Service to save waybill documents.

<a name="technical_details"/>

## Technical Details

The India Localization extension is a Java application that calls SAP Ariba's Partner Flow Extension API and fetches all intercepted ASN events. The events are persisted in a database and displayed in a SAPUI5 front-end.
The application user can upload document attachments to the intercepted ASN events. Once an attachment is being uploaded, the application resumes the related ASN.

To use this extension application, you need to:

1. Register an SAP Ariba Open APIs application in [SAP Ariba Developer Portal](https://developer.ariba.com/api).
2. Promote your registered application for production access.
3. Build and deploy the Java extension application on SAP Cloud Platform.
4. Configure the Java application connectivity.
5. Start the Java extension application.

<a name="prerequisites"/>

## Prerequisites

You need to:
* have an account for [SAP Ariba Developer Portal](https://developer.ariba.com/api)
* have an [SAP Cloud Platform developer account](https://help.hana.ondemand.com/help/frameset.htm?e4986153bb571014a2ddc2fdd682ee90.html)
* download or clone the project with Git
* have set up [Maven 3.0.x](http://maven.apache.org/docs/3.0.5/release-notes.html)

<a name="setup_ariba"/>

## Register a Partner Flow Extension Application in SAP Ariba Developer Portal

You already have an account for SAP Ariba Developer Portal. Open the [guide](https://developer.ariba.com/api/guides) and follow the steps to register a new SAP Ariba Open APIs application that will be used against SAP Ariba Open APIs.
At the end, you should have an application key related to the SAP Ariba Open APIs application.

As a next step, [promote your application for production access](https://developer.ariba.com/api/guides).
At the end, besides the application key, you will have a set of credentials (Service Provider user and Service Provider password) related to the registered SAP Ariba Open APIs application. You need them in order to call the Partner Flow Extension API production environment.

You will need the following Group Condition to intercept only ASNs with delivery across different states:

```xml
<GroupConditions>
		<Condition>ariba:not(ariba:string-compare( $src/cXML/Request/ShipNoticeRequest/ShipNoticeHeader/Contact[@role='shipFrom'][1]/PostalAddress/State, $src/cXML/Request/ShipNoticeRequest/ShipNoticeHeader/Contact[@role='shipTo'][1]/PostalAddress/State) )</Condition>
</GroupConditions>
```

<a name="build_deploy"/>

## Build and Deploy the Application on SAP Cloud Platform

You have already downloaded or cloned the India Localization extension application. Now you have to build the application and deploy it on the SAP Cloud Platform. There are two paths you can choose from: 

* using the SAP Cloud Platform Cockpit
* using the Eclipse IDE

<a name="build_deploy_cockpit"/>

### Using the SAP Cloud Platform Cockpit

#### Build the Application

1. Go to the `cloud-ariba-partner-flow-extension-ext` folder.
2. Build the project with:

        mvn clean install

The produced WAR file `ROOT.war` under target sub-folder `cloud-ariba-partner-flow-extension-ext\target` is ready to be deployed.

#### Deploy the Application Using the Cockpit

You have to [deploy](https://help.hana.ondemand.com/help/frameset.htm?abded969628240259d486c4b29b3948c.html) the `ROOT.war` file as a Java application via SAP Cloud Platform Cockpit. Use Java Web Tomcat 8 as a runtime option.

<a name="build_deploy_eclipse"/>

### Using the Eclipse IDE

When using the Eclipse IDE you can take a look at the structure and code of the application. You have to import the application as an existing Maven project and build it with Maven using `clean install`. You also have to choose Java Web Tomcat 8 as a runtime option.

#### Prerequisites

* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Eclipse IDE for Java EE Developers](https://eclipse.org/downloads/)
* [SAP Cloud Platform Tools](https://tools.hana.ondemand.com/#cloud) ([Installation help](https://help.hana.ondemand.com/help/frameset.htm?e815ca4cbb5710148376c549fd74c0db.html))

#### Build the Application from Eclipse

1. You have to clone the `cloud-ariba-partner-flow-extension-ext` project. 
      1. Оpen the _Git_ Perspective. Choose _Windows_ > _Open Perspective_ > _Other_. Select _Git_ and choose _OK_. 
      2. Choose _Clone a Git repository_.
      3. Enter `https://github.com/SAP/cloud-ariba-partner-flow-extension-ext.git` in the _URI_ field and choose _Next_.
      4. Set the _Directory_ field and choose _Finish_.
      
2. You have to import the `cloud-ariba-partner-flow-extension-ext` project as an existing Maven project and then build it.
      1. In the _Java EE_ perspective, choose _File_ > _Import_ > _Maven_ > _Existing Maven Project_. 
      2. Browse and select the folder where you have cloned the Git repository and choose _Finish_. Wait for the project to load.
      3. From the project context menu, choose _Run As_ > _Maven Build_.
      4. Enter `clean install` in the _Goals_ field and choose _Run_.
         The build should pass successfully.

#### Deploy the Application from Eclipse

To deploy the application from Eclipse IDE, follow these steps:

1. Set the context path to `/`
	1. In the _Project Explorer_ view right-click on the project and choose _Properties_ > _Web Project Settings_.
	2. For `Context root` enter `/`
2. In the _Servers_ view right-click on the white field and choose _New_ > _Server_. 
3. Select _SAP Cloud Platform_ and choose _Next_.
4. Select _Java Web Tomcat 8_ as a runtime option. 
5. Add the application to the _Configured_ field and choose _Finish_.

<a name="configure"/>

## Create a Destination

You need to [create an HTTP destination on the SAP Cloud Platform](https://help.hana.ondemand.com/help/frameset.htm?1e110da0ddd8453aaf5aed2485d84f25.html).


   Use the following required properties:

                Type: HTTP
                Name: ariba-india-localization
                URL: <SAP Ariba OpenAPIs production environment URL>
                Authentication: BasicAuthentication
                User: <SAP Ariba Open APIs Service Provider User>
                Password: <SAP Ariba Open APIs Service Provider Password>
		
   And add four additional properties:

                FlowExtensionId: <The unique flow extension id>
                ApiKey: <SAP Ariba Open APIs application API key>

   If you want to configure how often will the Partner Flow Extension API be called, add an additional property:
   
                JobIntervalInSeconds: <Not mandatory. How often will the Partner Flow Extension API be called>

>Note: There is a sample destination in the project's resources folder.

<a name="start"/>

## Start the Application

After creating the destination, [start (or restart in case the application is already started) the application via the Cloud Cockpit](https://help.hana.ondemand.com/help/frameset.htm?7612f03c711e1014839a8273b0e91070.html). 

## Test the Application
Create PO and ASN with delivery across different states. It should be intercepted by the extension application.

## Additional Information

<a name="additional_information_resources"/>

### Resources
* SAP Cloud Documentation - https://help.hana.ondemand.com/
* SAP Ariba OpenAPIs - https://developer.ariba.com/api

<a name="additional_information_license"/>

## License

Copyright © 2017-2020 SAP SE or an SAP affiliate company. All rights reserved. This project is licensed under the Apache Software License, version 2.0 except as noted otherwise in the [LICENSE file](LICENSES/Apache-2.0.txt).

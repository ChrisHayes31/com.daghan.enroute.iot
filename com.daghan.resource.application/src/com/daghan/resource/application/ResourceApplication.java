package com.daghan.resource.application;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.daghan.resource.web.rest.dto.DeviceConfiguration;

import osgi.enroute.configurer.api.RequireConfigurerExtender;
import osgi.enroute.google.angular.capabilities.RequireAngularWebResource;
import osgi.enroute.rest.api.REST;
import osgi.enroute.twitter.bootstrap.capabilities.RequireBootstrapWebResource;
import osgi.enroute.webserver.capabilities.RequireWebServerExtender;

@RequireAngularWebResource(resource = { "angular.js", "angular-resource.js", "angular-route.js" }, priority = 1000)
@RequireBootstrapWebResource(resource = "css/bootstrap.css")
@RequireWebServerExtender
@RequireConfigurerExtender
@Component(name = "com.daghan.resource")
public class ResourceApplication implements REST {

	public String getUpper(String string) {
		return string.toUpperCase();
	}
	
}

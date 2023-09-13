package org.keycloak.examples.domainextension.interceptor;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class TestFilter implements ContainerRequestFilter, ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("BLAAA");
		
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		System.out.println("BLAAA2");
	}

}

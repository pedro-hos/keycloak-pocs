package br.com.pedrohos.keycloak;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

@Provider
public class CustomLoggingFilter implements ContainerRequestFilter {

	Logger logger = Logger.getLogger(CustomLoggingFilter.class.getCanonicalName());

	@Context
	private HttpServletRequest servletRequest;

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		
		KeycloakSecurityContext session = getSession(); //session information
		logger.info("isLoggedIn()? " + isLoggedIn());
		
		if(isLoggedIn()) {
			
			AccessToken token = session.getToken(); //you have access to some user/token informations here!!!!
			
			logger.info("User Preferred Username: " + token.getPreferredUsername());
			logger.info("isLogoutAction()? " + isLogoutAction());
			
		}
	}

	private boolean isLoggedIn() {
		return getSession() != null;
	}

	public boolean isLogoutAction() {
		return getAction().equals("logout");
	}

	private String getAction() {
		
		if (servletRequest.getParameter("action") == null) {
			return "";
		}
		
		return servletRequest.getParameter("action");
	}

	private KeycloakSecurityContext getSession() {
		return (KeycloakSecurityContext) servletRequest.getAttribute(KeycloakSecurityContext.class.getName());
	}

}

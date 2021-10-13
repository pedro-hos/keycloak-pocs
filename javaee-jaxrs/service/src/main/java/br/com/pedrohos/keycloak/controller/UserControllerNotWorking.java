package br.com.pedrohos.keycloak.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 */

@Stateless
public class UserControllerNotWorking {

	private final Logger LOGGER = Logger.getLogger(UserControllerNotWorking.class.getName());

	@javax.annotation.Resource // this needs @Stateless
	private SessionContext sessionContext;

	public KeycloakPrincipal<KeycloakSecurityContext> getKeycloakPrincipal() {
		try {
			LOGGER.info("SessionContext Info: " + sessionContext.getCallerPrincipal().getName());
			@SuppressWarnings("unchecked")
			KeycloakPrincipal<KeycloakSecurityContext> kcPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) (sessionContext
					.getCallerPrincipal());
			return kcPrincipal;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}

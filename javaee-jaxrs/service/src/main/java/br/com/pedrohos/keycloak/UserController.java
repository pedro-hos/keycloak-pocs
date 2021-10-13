package br.com.pedrohos.keycloak;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 */

@Stateless
public class UserController {
	
	private final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	public void  getKeycloakPrincipal(HttpServletRequest servletRequest) {
		
		try {
			
			KeycloakSecurityContext session = getSession(servletRequest);
			
			if(session != null) {
				AccessToken token = session.getToken(); //you have access to some user/token
				LOGGER.info("User Preferred Username: " + token.getPreferredUsername());
				
				@SuppressWarnings("unchecked")
				KeycloakPrincipal<KeycloakSecurityContext> kcPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) servletRequest.getUserPrincipal();
				LOGGER.info("KeycloakPrincipal<KeycloakSecurityContext> " + kcPrincipal.getName());
				
			} else {
				LOGGER.info("Session is null!");
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	private KeycloakSecurityContext getSession(HttpServletRequest servletRequest) {
		return (KeycloakSecurityContext) servletRequest.getAttribute(KeycloakSecurityContext.class.getName());
	}

}

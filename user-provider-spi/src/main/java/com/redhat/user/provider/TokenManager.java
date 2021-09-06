package com.redhat.user.provider;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.UriBuilder;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.redhat.user.provider.pojo.AccessTokenResponse;
import com.redhat.user.provider.services.TokenService;
import com.redhat.user.provider.spi.MyUserStorageProvider;

@Stateful
@Local(TokenManager.class)
public class TokenManager {

	private static final Logger logger = Logger.getLogger(MyUserStorageProvider.class);
	
	private static final String REALM = "master";
	private static final String KEYCLOAK_ADM_USER = "admin";
	private static final String KEYCLOAK_ADM_PASS = "q1w2e3r4";
	private static final String KEYCLOAK_HOST = "http://localhost:8080/auth";
	private static final String CLIENT_ID = "admin-cli";
	
	public String getToken() {
		
		logger.info("Getting token ...");
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target(UriBuilder.fromPath(KEYCLOAK_HOST));
		ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
		TokenService tokenService = rtarget.proxy(TokenService.class);

		Form form = new Form().param("grant_type", "password")
				              .param("username", KEYCLOAK_ADM_USER)
				              .param("password", KEYCLOAK_ADM_PASS)
				              .param("client_id", CLIENT_ID);
		
		AccessTokenResponse accessTokenResponse = tokenService.grant(REALM, form.asMap());
		
		return accessTokenResponse.getToken();
	}
}

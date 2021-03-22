package com.redhat.resteasyclient;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.redhat.resteasyclient.pojo.AccessTokenResponse;
import com.redhat.resteasyclient.services.TokenService;

public class TokenManager {

	private static final String REALM = "master";
	private static final String KEYCLOAK_ADM_USER = "admin";
	private static final String KEYCLOAK_ADM_PASS = "q1w2e3r4";
	private static final String KEYCLOAK_HOST = "http://localhost:8080/auth";
	private static final String CLIENT_ID = "admin-cli";
	
	public static TokenManager getInstance() {
		return new TokenManager();
	}
	
	public String getToken() {
		
		ResteasyClient client = new ResteasyClientBuilder().register(CustomJacksonProvider.class)
														   .connectionPoolSize(10)
														   .build();
		
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(KEYCLOAK_HOST));
		TokenService tokenService = target.proxy(TokenService.class);

		Form form = new Form().param("grant_type", "password")
				              .param("username", KEYCLOAK_ADM_USER)
				              .param("password", KEYCLOAK_ADM_PASS)
				              .param("client_id", CLIENT_ID);
		
		AccessTokenResponse accessTokenResponse = tokenService.grant(REALM, form.asMap());
		
		return accessTokenResponse.getToken();
	}
}

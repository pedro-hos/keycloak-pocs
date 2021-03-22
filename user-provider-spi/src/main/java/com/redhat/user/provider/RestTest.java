package com.redhat.user.provider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("keycloak/")
public class RestTest {
	
	@GET
	@Path("token")
	@Produces(MediaType.TEXT_PLAIN)
	public Response token( ) {
		return Response.ok(TokenManager.getInstance().getToken()).build();
	}

}

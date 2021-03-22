package com.redhat.user.provider;

import javax.naming.InitialContext;
import javax.naming.NamingException;
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
	public Response token( ) throws NamingException {
		InitialContext ctx = new InitialContext();
		TokenManager provider = (TokenManager)ctx.lookup("java:global/user-provider-spi/TokenManager");
		return Response.ok(provider.getToken()).build();
	}

}

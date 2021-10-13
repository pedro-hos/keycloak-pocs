package br.com.pedrohos.keycloak.resources;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.pedrohos.keycloak.UserController;

@Path("/")
@Stateless
public class HelloResource {
	
	@Inject
	UserController userController;
	
	@Context
	private HttpServletRequest servletRequest;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("public")
	public Response getPublic() {
		return Response.ok().entity(new Message("public")).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("secured")
	public Response getSecured() {
		
		
		userController.getKeycloakPrincipal(servletRequest);
		
		return Response.ok().entity(new Message("secured")).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("admin")
	public Response getAdmin() {
		return Response.ok().entity(new Message("admin")).build();
	}

}

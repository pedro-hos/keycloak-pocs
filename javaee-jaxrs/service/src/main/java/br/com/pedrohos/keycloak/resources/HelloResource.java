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

import org.keycloak.KeycloakSecurityContext;

import br.com.pedrohos.keycloak.controller.UserControllerNotWorking;
import br.com.pedrohos.keycloak.controller.UserControllerWorkaround;

@Path("/")
@Stateless
public class HelloResource {
	
	@Inject
	UserControllerWorkaround userController;
	
	@Inject
	UserControllerNotWorking userControllerNotWorking;
	
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
		//userControllerNotWorking.getKeycloakPrincipal();
		return Response.ok().entity(new Message("secured")).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("admin")
	public Response getAdmin() {
		return Response.ok().entity(new Message("admin")).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sessionId")
	public Response getSession() {
		KeycloakSecurityContext context = (KeycloakSecurityContext) servletRequest.getAttribute(KeycloakSecurityContext.class.getName());
		String sessionId = context.getToken().getSessionState();
		return Response.ok().entity(new Message(sessionId)).build();
	}
	

}

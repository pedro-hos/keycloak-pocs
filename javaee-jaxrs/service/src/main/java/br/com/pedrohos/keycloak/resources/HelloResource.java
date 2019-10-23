package br.com.pedrohos.keycloak.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloResource {

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
		return Response.ok().entity(new Message("secured")).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("admin")
	public Response getAdmin() {
		return Response.ok().entity(new Message("admin")).build();
	}

}

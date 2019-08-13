package br.com.pedrohos;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.KeycloakSecurityContext;

@Path("/api")
public class MyResource {

	@Inject
	KeycloakSecurityContext keycloakSecurityContext;

	@GET
	@Path("/me")
	@RolesAllowed("user")
	@Produces(MediaType.APPLICATION_JSON)
	@NoCache
	public Usuario minhasInfos() {
		return new Usuario(keycloakSecurityContext);
	}
	
	@GET
	@Path("/admin")
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String admin() {
        return "Admin infos";
    }

}

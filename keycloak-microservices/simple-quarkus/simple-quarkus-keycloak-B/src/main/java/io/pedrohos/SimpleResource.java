package io.pedrohos;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;

import io.quarkus.security.identity.SecurityIdentity;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
@Path("/api")
@Produces("text/plain")
public class SimpleResource {

    @Inject
    SecurityIdentity identity;
    
    @GET
    @Path("hello")
    public Response hello() {
        return Response.ok("Hello app B").build();
    }

    @GET
    @Path("/me")
    @NoCache
    public Response me() {
        return Response.ok(identity.getPrincipal().getName()).build();
    }
}

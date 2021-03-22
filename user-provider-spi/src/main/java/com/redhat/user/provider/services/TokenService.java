package com.redhat.user.provider.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.redhat.user.provider.pojo.AccessTokenResponse;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface TokenService {
	
	@POST
    @Path("/realms/{realm}/protocol/openid-connect/token")
	AccessTokenResponse grant(@PathParam("realm") String realm, MultivaluedMap<String, String> map);	

}

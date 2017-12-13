package com.example.swarmkeycloak.rest;

import java.net.URI;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.swarmkeycloak.model.entities.Livro;
import com.example.swarmkeycloak.model.repositories.impl.Livros;

@Path("livros")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LivroEndpoint {
	
	@Inject
	private Livros livros;
	
	@POST
	public Response novo(Livro livro) {
		
		livros.adicionar(livro);
		
		return Response.created(URI.create("/livros/" + livro.getId()))
					   .entity(livro)
					   .build();
	}
	
	@PUT
	public Response atualiza(Livro livro) {
		livros.atualizar(livro);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		livros.remover(livros.comId(id));
		return Response.ok().build();
	}
	
	@GET
	public Response todos() {
		return Response.ok(livros.todos()).build();
	}

}

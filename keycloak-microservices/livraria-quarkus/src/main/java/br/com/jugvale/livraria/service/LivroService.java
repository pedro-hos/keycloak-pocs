package br.com.jugvale.livraria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.jugvale.livraria.model.Livro;

@Path("estoque/livro")
@RegisterRestClient
public interface LivroService {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	List<Livro> buscaTodosLivros();
	
	@PUT
	@Path("/remover/{isbn}/quantidade/{quantidade}")
	@Produces(MediaType.APPLICATION_JSON)
	Livro vender(@HeaderParam("Authorization") String authHeaderValue, @PathParam("isbn") final String isbn, @PathParam("quantidade") final int quantidade);
	
	/*
	 * @PUT
	 * 
	 * @Path("/remover/{isbn}/quantidade/{quantidade}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) Livro vender(@PathParam("isbn") final
	 * String isbn, @PathParam("quantidade") final int quantidade);
	 */
}

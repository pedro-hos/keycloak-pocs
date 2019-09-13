package br.com.jugvale.livraria.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.keycloak.KeycloakSecurityContext;

import br.com.jugvale.livraria.model.Livro;
import br.com.jugvale.livraria.model.Venda;
import br.com.jugvale.livraria.service.LivroService;

@Path("/api/livraria")
public class LivrariaResource {

	@Inject
	@RestClient
	LivroService livroService;
	
	@Inject
	KeycloakSecurityContext keycloakSecurityContext;

	@GET
	@Path("/livros")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Livro> buscaLivros() {
		return livroService.buscaTodosLivros();
	}

	@PUT
	@Path("livro/{isbn}/vender/{quantidade}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("vendedor")
	@Transactional
	public Livro vender(@PathParam("isbn") final String isbn, @PathParam("quantidade") final int quantidade) {

		try {

			Livro livro = livroService.vender("Bearer " + keycloakSecurityContext.getTokenString(), isbn, quantidade);

			Venda venda = new Venda();
			venda.setIsbn(isbn);
			venda.setQuantidade(quantidade);
			venda.persist();

			return livro;

		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.status(Status.CONFLICT).build());
		}

	}

	@GET
	@Path("/vendas")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("gerente")
	public Response buscaVendas() {
		return Response.ok(Venda.listAll()).build();
	}

}

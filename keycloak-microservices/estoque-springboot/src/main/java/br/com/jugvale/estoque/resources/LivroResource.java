package br.com.jugvale.estoque.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.jugvale.estoque.model.entity.Livro;
import br.com.jugvale.estoque.model.repository.LivroRepository;

@RestController
@RequestMapping("/api/estoque/livro")
public class LivroResource {

	@Autowired
	private LivroRepository livroRepository;
	
	@GetMapping(value = {"/", ""}, produces = "application/json")
	public List<Livro> livros() {
		return livroRepository.findAll();
	}
	
	@GetMapping(value = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Livro comIsbn(@PathVariable final String isbn) {
		
		Livro livro = livroRepository.findByIsbn(isbn);
		
		if(livro == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Achei essa ISBN não!");
		}
		
		return livro;
	}
	
	@PutMapping(value="/remover/{isbn}/quantidade/{quantidade}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Livro retiraEstoque(@PathVariable final String isbn, @PathVariable final int quantidade) {
		
		Livro livro = livroRepository.findByIsbn(isbn);
		
		if(quantidade > livro.getQuantidade()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Não tem tudo isso em estoque chapa!");
		}
		
		livro.setQuantidade(livro.getQuantidade() - quantidade);
		livroRepository.save(livro);
		
		return livro;
	}
	
}

package br.com.jugvale.estoque.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jugvale.estoque.model.entity.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
	Livro findByIsbn(final String isbn);
}

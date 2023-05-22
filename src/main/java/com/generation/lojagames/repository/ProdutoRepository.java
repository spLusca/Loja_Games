package com.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.lojagames.model.Categoria;
import com.generation.lojagames.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	 List<Produto> findByNomeContainingIgnoreCase(String nome);
	 List<Produto> findByCategoria(Categoria categoria);
}

package com.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.lojagames.model.Categoria;
import com.generation.lojagames.model.Produto;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private final ProdutoRepository produtoRepository;
	
	@Autowired
	private final CategoriaRepository categoriaRepository;

	public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
		this.produtoRepository = produtoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping
	public ResponseEntity<List<Produto>> getAllProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		return produtoOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Produto> createProduto(@Valid @RequestBody Produto produto) {
		Produto produtoCriado = produtoRepository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @Valid @RequestBody Produto produtoAtualizado) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		if (produtoOptional.isPresent()) {
			Produto produto = produtoOptional.get();
			produto.setNome(produtoAtualizado.getNome());
			produto.setCategoria(produtoAtualizado.getCategoria());
			Produto produtoAtualizadoEntity = produtoRepository.save(produto);
			return ResponseEntity.ok(produtoAtualizadoEntity);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		if (produtoOptional.isPresent()) {
			produtoRepository.delete(produtoOptional.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}/categoria")
	public ResponseEntity<Categoria> getProdutoCategoria(@PathVariable Long id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		if (produtoOptional.isPresent()) {
			Produto produto = produtoOptional.get();
			Categoria categoria = produto.getCategoria();
			return ResponseEntity.ok(categoria);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}

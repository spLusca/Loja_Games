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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	@Autowired
	private final CategoriaRepository categoriaRepository;

	public CategoriaController(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping
	public ResponseEntity<List<Categoria>> getAllCategorias() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return ResponseEntity.ok(categorias);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		return categoriaOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria categoriaCriada = categoriaRepository.save(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id,
			@Valid @RequestBody Categoria categoriaAtualizada) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) {
			Categoria categoria = categoriaOptional.get();
			categoria.setNome(categoriaAtualizada.getNome());
			Categoria categoriaAtualizadaEntity = categoriaRepository.save(categoria);
			return ResponseEntity.ok(categoriaAtualizadaEntity);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) {
			categoriaRepository.delete(categoriaOptional.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}/produtos")
	public ResponseEntity<List<Produto>> getCategoriaProdutos(@PathVariable Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) {
			Categoria categoria = categoriaOptional.get();
			List<Produto> produtos = categoria.getProdutos();
			return ResponseEntity.ok(produtos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
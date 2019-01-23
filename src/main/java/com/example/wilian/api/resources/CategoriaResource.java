package com.example.wilian.api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wilian.api.model.Categoria;
import com.example.wilian.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
//	@GetMapping
//	public ResponseEntity<?> listar(){
//		List<Categoria> categorias = categoriaRepository.findAll();
//		return categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
//	}
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}

}

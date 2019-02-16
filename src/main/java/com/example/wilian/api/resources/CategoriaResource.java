package com.example.wilian.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.wilian.api.event.RecursoCriadoEvent;
import com.example.wilian.api.model.Categoria;
import com.example.wilian.api.model.Pessoa;
import com.example.wilian.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
//	@GetMapping
//	public ResponseEntity<?> listar(){
//		List<Categoria> categorias = categoriaRepository.findAll();
//		return categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
//	}
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response){
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		
		//devolve o status e o body da categoria salva
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	}
	
	@GetMapping("/{id}")
	public Categoria buscarPeloCodigo(@PathVariable Integer id){
		return categoriaRepository.findOne(id);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Integer id) {
		categoriaRepository.delete(id);;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria>alter(@PathVariable Integer id, @Valid @RequestBody Categoria categoria){
		Categoria categoriaSalva = categoriaRepository.findOne(id);
		if(categoriaSalva == null){
			throw new EmptyResultDataAccessException(1);
		}
		//classe muito interessante para copiar uma classe
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		categoriaRepository.save(categoriaSalva);
		return ResponseEntity.ok(categoriaSalva);
	}

}

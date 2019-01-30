package com.example.wilian.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wilian.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	
}

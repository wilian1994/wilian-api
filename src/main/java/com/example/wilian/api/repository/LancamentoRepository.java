package com.example.wilian.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wilian.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}

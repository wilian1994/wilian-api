package com.example.wilian.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wilian.api.model.Lancamento;
import com.example.wilian.api.resources.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}

package com.example.wilian.api.repository;

import java.util.List;

import com.example.wilian.api.model.Lancamento;
import com.example.wilian.api.resources.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}

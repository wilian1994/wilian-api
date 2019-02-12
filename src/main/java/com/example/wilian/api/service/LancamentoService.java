package com.example.wilian.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.wilian.api.exception.PessoaInexistenteOuInativaException;
import com.example.wilian.api.model.Lancamento;
import com.example.wilian.api.model.Pessoa;
import com.example.wilian.api.repository.LancamentoRepository;
import com.example.wilian.api.repository.PessoaRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento buscarPessoa(Long id) {
		Lancamento lancamento = lancamentoRepository.findOne(id);

		if (lancamento == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamento;
	}

}

package com.example.wilian.api.resources;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wilian.api.event.RecursoCriadoEvent;
import com.example.wilian.api.exception.PessoaInexistenteOuInativaException;
import com.example.wilian.api.exception.WilianExceptionHandler.Erro;
import com.example.wilian.api.model.Lancamento;
import com.example.wilian.api.repository.LancamentoRepository;
import com.example.wilian.api.resources.filter.LancamentoFilter;
import com.example.wilian.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	LancamentoRepository lancamentoRepository;

	@Autowired
	LancamentoService lancamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	MessageSource messageSource;

	@GetMapping
	public List<Lancamento> pesquisar(LancamentoFilter filter) {
		return lancamentoRepository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Lancamento> criar(@RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);

		// Pegar apartir da requisição atual adicionar o código na URI, usado
		// para fazer redirect, ou quando um novo recurso foi criado
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

		// devolve o status e o body da pessoa salva
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);

	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlepessoaInexistenteOuInativaException(
			PessoaInexistenteOuInativaException ex) {
		String mensagemInvalida = messageSource.getMessage("mensagem.inativa.inexistente", null,
				LocaleContextHolder.getLocale());
		String mensagemDev = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemInvalida, mensagemDev));
		return ResponseEntity.badRequest().body(erros);
	}
}

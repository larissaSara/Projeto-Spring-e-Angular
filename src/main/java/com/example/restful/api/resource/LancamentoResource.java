package com.example.restful.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.restful.api.event.RecursoCriadoEvent;
import com.example.restful.api.exceptionhandler.ExceptionHandler.Erro;
import com.example.restful.api.model.Lancamento;
import com.example.restful.api.repository.LancamentoRepository;
import com.example.restful.api.repository.filter.Lancamentofilter;
import com.example.restful.api.service.LancamentoService;
import com.example.restful.api.service.exception.PessoaInexistenteOuInativoException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private MessageSource messageSource;
	
	//@GetMapping
	//public List<Lancamento> listar(){
		//return lancamentoRepository.findAll();
	//}
	
	@GetMapping
	public Page<Lancamento> pesquisar(Lancamentofilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarCodigo(@PathVariable Long codigo){
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		return lancamento!=null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		
		Lancamento lancamentosalvo = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentosalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentosalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo){
		lancamentoRepository.delete(codigo);
		
	}
	
	//excecao de pessoa inativa
	@ExceptionHandler({PessoaInexistenteOuInativoException.class})
	public ResponseEntity<Object> handlerPessoaInexistenteOuInativoException(PessoaInexistenteOuInativoException ex){
		String mensagemUsuario = messageSource.getMessage("mensagem.pessoa-inativa-ou-inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario,mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}

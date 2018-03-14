package com.example.restful.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restful.api.model.Lancamento;
import com.example.restful.api.model.Pessoa;
import com.example.restful.api.repository.LancamentoRepository;
import com.example.restful.api.repository.PessoaRepository;
import com.example.restful.api.service.exception.PessoaInexistenteOuInativoException;

@Service
public class LancamentoService {
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	//verificacao se pessoa esta inativa ou nao
	//se estiver inativa nao salva nenhum lancamento
	//se estiver ativa salva
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativoException();
		}
		return lancamentoRepository.save(lancamento);
	}

}

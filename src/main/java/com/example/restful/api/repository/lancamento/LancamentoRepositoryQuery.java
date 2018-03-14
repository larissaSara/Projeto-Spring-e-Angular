package com.example.restful.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.restful.api.model.Lancamento;
import com.example.restful.api.repository.filter.Lancamentofilter;

public interface LancamentoRepositoryQuery {
	public Page<Lancamento> filtrar(Lancamentofilter lancamentoFilter, Pageable pageable);
}

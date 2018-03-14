package com.example.restful.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restful.api.model.Lancamento;
import com.example.restful.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}

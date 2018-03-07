package com.example.restful.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restful.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}

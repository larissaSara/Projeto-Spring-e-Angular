package com.example.restful.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restful.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	//extendendo o jparepository possui os metodos que iremos trabalhar
	
}

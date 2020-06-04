package com.generation.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.produtos.model.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Long> {

}

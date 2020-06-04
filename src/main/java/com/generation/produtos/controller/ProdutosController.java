package com.generation.produtos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.generation.produtos.model.Produto;
import com.generation.produtos.repository.ProdutosRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutosRepository produtoRepository;
	
	@GetMapping
	public List<Produto> listar () {
		List<Produto> produtos = produtoRepository.findAll();
		
		return produtos;
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Produto> listarProduto (@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if(produto.isPresent()) {
			return ResponseEntity.ok(produto.get());
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Produto> cadastrar (@RequestBody Produto produto, UriComponentsBuilder uriBuilder) {
	
		Produto produtos = produtoRepository.save(produto);
		URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(produtos);
	}
	
	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<Produto> atualizar (@PathVariable Long id, @RequestBody Produto produto) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if(optional.isPresent()) {
			Produto produtoAntigo = optional.get();
			produtoAntigo.setDescricao(produto.getDescricao());
			produtoAntigo.setNome(produto.getNome());
			produtoAntigo.setPreco(produto.getPreco());
			produtoAntigo.setUrlImg(produto.getUrlImg());
			
			return ResponseEntity.ok(produtoAntigo);
			
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Produto> remover (@PathVariable Long id) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent()) {
			produtoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}

package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "temas")
public class Tema {
	
	@Id //@id Indica que é uma chave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) //@generatedValue é um autoincrement
	private Long id;
	
	@NotBlank (message = "A Descrição é Obrigatoria!")
	@Size(min = 10, max = 1000, message = "A Descrição Deve Ter No Minimo 10 Caracteres e No Maximo 1000 Caracteres!")
	private String descricao;

	//fetchtype.lazy faz uma busca um por um | mappedBy realiza um mapeamento | 
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tema", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("tema")
	private List<Postagem> postagem;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	
	
	
}

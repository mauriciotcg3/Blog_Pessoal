package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "postagens")
public class Postagem {

	@Id //Primary Key = ID
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Será gerada automaticamente
	private Long id;
	
	@NotBlank(message = "O Titulo é Obrigatorio!!") //notblank para textos
	@Size(min = 5, max = 100, message = "O titulo Deve Ter No Minimo 5 Caracteres e No Maximo 100 Caracteres!") //Tamanho para definir
	private String titulo;
	
	@NotBlank(message = "O Texto é Obrigatorio!!")
	@Size(min = 10, max = 1000, message = "O Texto Deve Ter No Minimo 10 Caracteres e No Maximo 1000 Caracteres!")
	private String texto;
	
	@UpdateTimestamp
	private LocalDateTime data;

	@ManyToOne //muitos para um
	@JsonIgnoreProperties("postagens")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagens")
	private Usuario usuario;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
}

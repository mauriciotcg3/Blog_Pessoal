package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		
		usuarioRepository.deleteAll();
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", " ", null));
		
	}
	
	@Test
	@DisplayName("Cadastrar um Usuario")
	public void deveCriarUmUsuario() {
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Paulo Tartari", 
				"paulo_tartari@gmail.com", "maymay123", "-", null));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
		
	}

	@Test
	@DisplayName("Não Deve Permitir Duplicação Do Usuario")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Tiago Meconi", 
				"tiago_meconi@gmail.com", "blaze123", "-", null));
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Tiago Meconi", "tiago_meconi@gmail.com", "blaze123", "-", null));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
		
	}
	
	@Test
	@DisplayName("Atualiza o Usuario")
	public void atualizaUsuario() {
		
		Optional<Usuario> usuarioCadastro = usuarioService.cadastrarUsuario(new Usuario(0L,
				"Pedro Macioni", "pedro_macioni@gmail.com", "pedrinho123", "-", null));
		
		Usuario usuarioUpdate = new Usuario(usuarioCadastro.get().getId(),
				"Pedro Macioni", "pedro_macioni@gmail.com", "pedrinho123", "-", null);
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar Todos Os Usuarios")
	public void deveMostrarTodosUsuarios() {
	
		usuarioService.cadastrarUsuario(new Usuario(0L, "Maurício Freire", 
				"mauricio_freire@gmail.com", "maumau123", "-", null));
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Pedro Macioni", "pedro_macioni@gmail.com", "pedrinho123", "-", null));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}
	
	
}

package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@SpringBootTest
class REQ01CadastrarLivroTests {
	@Autowired
	LivroRepository repository;
	Livro livro;
	private Validator validator;
	private ValidatorFactory validatorFactory;

	@Test
	void ct01_quando_dados_validos_isbn_nao_cadastrado_retorna1() {
		
		// Dado – que o atendente tem um livro não cadastrado
		repository.deleteAll();
		// Quando – o atendente cadastra o livro
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		// Então – o sistema verifica os dados E confirma a operação
		assertEquals(1, repository.count());
	}

	@Test
	void ct02_quando_dados_validos_violacoes_retorna_vazio() {
		// Dado – que o atendente tem um livro não cadastrado
		repository.deleteAll();
		// Quando – o atendente cadastra um livro com informações válidas
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		// Então – o sistema verifica os dados E confirma a operação.
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		assertTrue(violations.isEmpty());
	}

	@Test
	void ct03_quando_titulo_branco_retorna_msg_titulo_invalido() {
		// Dado – existem regras para validacao da entrada de dados
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		repository.deleteAll();
		// Quando – o usuário nao informa o titulo e confirma a operação
		Livro livro = new Livro("3333", "", "Delamaro");
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// Então – o sistema valida as informações E retorna mensagem titulo invalido.
		// assertFalse(violations.isEmpty());
		// assertEquals(violations.size(), 1);
		assertEquals("Titulo deve ter entre 1 e 50 caracteres", violations.iterator().next().getMessage());
	}
}

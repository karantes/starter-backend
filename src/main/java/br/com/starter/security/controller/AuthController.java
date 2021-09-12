package br.com.starter.security.controller;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.starter.security.dto.UserDTO;
import br.com.starter.security.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody UserDTO dto) {
		return ResponseEntity.ok(userService.signin(dto));
	}

	@PostMapping("/alter-password")
	public ResponseEntity<?> alterPassword(@RequestBody UserDTO dto) {

		if (userService.alterPassword(dto))
			return ResponseEntity.ok("Password altered");
		else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erro ao alterar a senha");
	}

	@PostMapping("/esqueci-minha-senha")
	public ResponseEntity<?> esqueciMinhaSenha(@RequestBody UserDTO dto) {

		try {
			userService.enviaEmailRecuperacaoSenha(dto);
		} catch (UnknownHostException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erro ao enviar email");
		}

		return ResponseEntity.ok("Email enviado!");
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO dto) {

		try {
			userService.criaNovoUsuario(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}

	}
}

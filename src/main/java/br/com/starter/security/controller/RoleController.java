package br.com.starter.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.starter.config.query.Request;
import br.com.starter.security.dto.RoleDTO;
import br.com.starter.security.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	private RoleService service;

	@PostMapping(value = "/add")
	public ResponseEntity<?> save(@RequestBody RoleDTO dto) {
		dto = (RoleDTO) service.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> update(@RequestBody RoleDTO dto) {
		dto = (RoleDTO) service.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@PostMapping(value = "/find")
	public ResponseEntity<?> findAll(@RequestBody Request request) {
		return ResponseEntity.ok(service.findAll(request));
	}

	@GetMapping(value = "/{id}")
	@CrossOrigin
	public ResponseEntity<?> findOne(@PathVariable int id) {
		return ResponseEntity.ok(service.findById(id));
	}
}

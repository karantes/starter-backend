package br.com.starter.security.dto;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.starter.config.DTO;
import br.com.starter.security.entity.User;
import br.com.util.UtilReflection;

public class UserDTO extends DTO {

	private Long id;

	private String name;

	private String username;

	private String email;

	private String password;

	private Set<RoleDTO> roles = new HashSet<>();

	private String token;

	private Boolean primeiroLogin;

	private String hashAlterarSenha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean isPrimeiroLogin() {
		return primeiroLogin;
	}

	public void setPrimeiroLogin(Boolean primeiroLogin) {
		this.primeiroLogin = primeiroLogin;
	}

	public String getHashAlterarSenha() {
		return hashAlterarSenha;
	}

	public void setHashAlterarSenha(String hashAlterarSenha) {
		this.hashAlterarSenha = hashAlterarSenha;
	}

	public String getUsernameOrEmail() {
		return username == null || username.isEmpty() ? email : username;
	}

	public UserDTO(User entity) {
		if (entity != null) {
			final Field[] fields = entity.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					if (fields[i].getName().equals("roles")) {
						this.setRoles(entity.getRoles().stream().map(p -> new RoleDTO(p)).collect(Collectors.toSet()));
					} else {
						Object valor = UtilReflection.getValor(fields[i].getName(), entity);
						UtilReflection.setValor(fields[i].getName(), this, valor);
					}
				} catch (Exception e) {
					UtilReflection.setValor(fields[i].getName(), this, null);
				}
			}
		}
	}

	public UserDTO() {
	}

}

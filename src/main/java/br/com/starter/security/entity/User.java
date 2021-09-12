package br.com.starter.security.entity;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import br.com.starter.security.dto.UserDTO;
import br.com.util.UtilReflection;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "tx_username" }),
		@UniqueConstraint(columnNames = { "tx_email" }) })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tx_name")
	private String name;

	@Column(name = "tx_username", unique = true)
	private String username;

	@NaturalId
	@Column(name = "tx_email", unique = true)
	private String email;

	@Column(name = "tx_password")
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Column(name = "tx_token")
	private String token;

	@Column(name = "bl_primeiro_login")
	private Boolean primeiroLogin;

	private String hashAlterarSenha;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User(UserDTO dto) {
		if (dto != null) {
			final Field[] fields = dto.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					if (fields[i].getName().equals("roles")) {
						if (dto.getRoles() != null)
							this.setRoles(dto.getRoles().stream().map(p -> new Role(p)).collect(Collectors.toSet()));
					} else {
						Object valor = UtilReflection.getValor(fields[i].getName(), dto);
						UtilReflection.setValor(fields[i].getName(), this, valor);
					}
				} catch (Exception e) {
					UtilReflection.setValor(fields[i].getName(), this, null);
				}
			}
		}
	}

	public User() {
	}

	public User(String name, String username, String email, String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(User entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.username = entity.getUsername();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
		this.roles = entity.getRoles();
		this.token = entity.getToken();
		this.hashAlterarSenha = entity.getHashAlterarSenha();
		this.primeiroLogin = entity.isPrimeiroLogin();
	}

}

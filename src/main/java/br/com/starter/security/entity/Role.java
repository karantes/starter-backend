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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import br.com.starter.admin.entity.FuncionalidadeFront;
import br.com.starter.admin.entity.Menu;
import br.com.starter.security.dto.RoleDTO;
import br.com.util.UtilReflection;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NaturalId
	@Column(length = 60, unique = true)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "roles_menus", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private Set<Menu> menus = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "roles_funcionalidades_front", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "funcionalidade_front_id"))
	private Set<FuncionalidadeFront> funcionalidadesFront = new HashSet<>();

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

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<FuncionalidadeFront> getFuncionalidadesFront() {
		return funcionalidadesFront;
	}

	public void setFuncionalidadesFront(Set<FuncionalidadeFront> funcionalidadesFront) {
		this.funcionalidadesFront = funcionalidadesFront;
	}

	public Role(RoleDTO dto) {
		if (dto != null) {
			final Field[] fields = dto.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					if (fields[i].getName().equals("menus")) {
						this.setMenus(dto.getMenus().stream().map(p -> new Menu(p)).collect(Collectors.toSet()));
					} else if (fields[i].getName().equals("funcionalidadesFront")) {
						this.setFuncionalidadesFront(dto.getFuncionalidadesFront().stream()
								.map(p -> new FuncionalidadeFront(p)).collect(Collectors.toSet()));
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

	public Role() {
	}

}

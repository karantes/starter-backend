package br.com.starter.security.dto;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.starter.admin.dto.FuncionalidadeFrontDTO;
import br.com.starter.admin.dto.MenuDTO;
import br.com.starter.config.DTO;
import br.com.starter.security.entity.Role;
import br.com.util.UtilReflection;

public class RoleDTO extends DTO {

	private Long id;

	private String name;

	private Set<MenuDTO> menus = new HashSet<>();

	private Set<FuncionalidadeFrontDTO> funcionalidadesFront = new HashSet<>();

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

	public Set<MenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuDTO> menus) {
		this.menus = menus;
	}

	public Set<FuncionalidadeFrontDTO> getFuncionalidadesFront() {
		return funcionalidadesFront;
	}

	public void setFuncionalidadesFront(Set<FuncionalidadeFrontDTO> funcionalidadesFront) {
		this.funcionalidadesFront = funcionalidadesFront;
	}

	public RoleDTO(Role entity) {
		if (entity != null) {
			final Field[] fields = entity.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					if (fields[i].getName().equals("menus")) {
						this.setMenus(entity.getMenus().stream().map(p -> new MenuDTO(p)).collect(Collectors.toSet()));
					} else if (fields[i].getName().equals("funcionalidadesFront")) {
						this.setFuncionalidadesFront(entity.getFuncionalidadesFront().stream()
								.map(p -> new FuncionalidadeFrontDTO(p)).collect(Collectors.toSet()));
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

	public RoleDTO() {
	}

}

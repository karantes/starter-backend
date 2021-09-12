package br.com.starter.admin.dto;

import java.lang.reflect.Field;

import org.springframework.web.bind.annotation.RequestMethod;

import br.com.starter.admin.entity.FuncionalidadeFront;
import br.com.util.UtilReflection;

public class FuncionalidadeFrontDTO {

	private Long id;

	private String name;

	private String value;

	private RequestMethod method;

	private MenuDTO menu;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}

	public MenuDTO getMenu() {
		return menu;
	}

	public void setMenu(MenuDTO menu) {
		this.menu = menu;
	}

	public FuncionalidadeFrontDTO(FuncionalidadeFront entity) {
		if (entity != null) {
			final Field[] fields = entity.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					if (fields[i].getName().equals("menu")) {
						if (entity.getMenu() != null)
							this.setMenu(new MenuDTO(entity.getMenu()));
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

	public FuncionalidadeFrontDTO() {

	}

}

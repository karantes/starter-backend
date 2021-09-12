package br.com.starter.admin.dto;

import java.lang.reflect.Field;
import java.util.Objects;

import br.com.starter.admin.entity.Menu;
import br.com.starter.config.DTO;
import br.com.util.UtilReflection;

public class MenuDTO extends DTO {

	private Long id;

	private String name;

	private String value;

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

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MenuDTO other = (MenuDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	public MenuDTO(Menu entity) {
		if (entity != null) {
			final Field[] fields = entity.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					Object valor = UtilReflection.getValor(fields[i].getName(), entity);
					UtilReflection.setValor(fields[i].getName(), this, valor);
				} catch (Exception e) {
					UtilReflection.setValor(fields[i].getName(), this, null);
				}
			}
		}
	}

	public MenuDTO() {
	}

}

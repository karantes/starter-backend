package br.com.starter.admin.entity;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.starter.admin.dto.FuncionalidadeFrontDTO;
import br.com.util.UtilReflection;

@Entity
@Table(name = "funcionalidades_front", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "tx_name", "tx_value", "tx_method" }) })
public class FuncionalidadeFront {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tx_name")
	private String name;

	@Column(name = "tx_value")
	private String value;

	@Column(name = "tx_method")
	@Enumerated(EnumType.STRING)
	private RequestMethod method;

	@OneToOne
	@Fetch(FetchMode.SELECT)
	private Menu menu;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(id, method, name, value);
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
		FuncionalidadeFront other = (FuncionalidadeFront) obj;
		return Objects.equals(id, other.id) && method == other.method && Objects.equals(name, other.name)
				&& Objects.equals(value, other.value);
	}

	public FuncionalidadeFront(FuncionalidadeFrontDTO dto) {
		if (dto != null) {
			final Field[] fields = dto.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					if (fields[i].getName().equals("menu")) {
						if (dto.getMenu() != null)
							this.setMenu(new Menu(dto.getMenu()));
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

	public FuncionalidadeFront() {

	}
}

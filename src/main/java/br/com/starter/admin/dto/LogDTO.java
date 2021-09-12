package br.com.starter.admin.dto;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import br.com.starter.admin.entity.Log;
import br.com.starter.config.DTO;
import br.com.util.UtilReflection;

public class LogDTO extends DTO {

	private Long id;

	private Integer status;

	private String metodo;

	private String uri;

	private String metodoJava;

	private LocalDateTime dtEvento;

	private String user;

	private String ip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMetodoJava() {
		return metodoJava;
	}

	public void setMetodoJava(String metodoJava) {
		this.metodoJava = metodoJava;
	}

	public LocalDateTime getDtEvento() {
		return dtEvento;
	}

	public void setDtEvento(LocalDateTime dtEvento) {
		this.dtEvento = dtEvento;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public LogDTO(Log entity) {
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

	public LogDTO() {
	}

}

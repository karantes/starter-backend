package br.com.starter.admin.entity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.starter.admin.dto.LogDTO;
import br.com.util.UtilReflection;

@Entity
@Table(name = "logs", uniqueConstraints = { @UniqueConstraint(columnNames = { "nr_status", "tx_metodo", "tx_uri",
		"tx_metodo_java", "dt_evento", "tx_user", "tx_ip" }) })
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nr_status")
	private Integer status;

	@Column(name = "tx_metodo")
	private String metodo;

	@Column(name = "tx_uri")
	private String uri;

	@Column(name = "tx_metodo_java")
	private String metodoJava;

	@Column(name = "dt_evento")
	private LocalDateTime dtEvento;

	@Column(name = "tx_user")
	private String user;

	@Column(name = "tx_ip")
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
	
	public Log(LogDTO dto) {
		if (dto != null) {
			final Field[] fields = dto.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					Object valor = UtilReflection.getValor(fields[i].getName(), dto);
					UtilReflection.setValor(fields[i].getName(), this, valor);
				} catch (Exception e) {
					UtilReflection.setValor(fields[i].getName(), this, null);
				}
			}
		}
	}

	public Log() {
	}

}
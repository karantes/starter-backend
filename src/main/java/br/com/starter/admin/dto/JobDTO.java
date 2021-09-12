package br.com.starter.admin.dto;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import br.com.starter.admin.entity.Job;
import br.com.starter.admin.entity.UnidadeTempo;
import br.com.starter.config.DTO;
import br.com.util.UtilReflection;

public class JobDTO extends DTO {

	private Long id;

	private String name;

	private Boolean ativo;

	private Integer tempo;

	private UnidadeTempo unidadeTempo;

	private LocalDateTime ultimaExecucao;

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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public UnidadeTempo getUnidadeTempo() {
		return unidadeTempo;
	}

	public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
		this.unidadeTempo = unidadeTempo;
	}

	public LocalDateTime getUltimaExecucao() {
		return ultimaExecucao;
	}

	public void setUltimaExecucao(LocalDateTime ultimaExecucao) {
		this.ultimaExecucao = ultimaExecucao;
	}

	public JobDTO(Job entity) {
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

	public JobDTO() {
	}
}

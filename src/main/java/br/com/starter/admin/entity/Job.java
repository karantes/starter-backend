package br.com.starter.admin.entity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.starter.admin.dto.JobDTO;
import br.com.util.UtilReflection;

@Entity
@Table(name = "jobs")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tx_name", unique = true)
	private String name;

	@Column(name = "bl_ativo", nullable = false)
	private Boolean ativo;

	@Column(name = "nr_tempo", nullable = false)
	private Integer tempo;

	@Column(name = "tx_unidade_tempo", nullable = false)
	@Enumerated(EnumType.STRING)
	private UnidadeTempo unidadeTempo;

	@Column(name = "dt_ultima_execucao")
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

	public Job() {
	}

	/**
	 * @param name
	 * @param ativo
	 * @param tempo
	 * @param unidadeTempo
	 */
	public Job(String name, Boolean ativo, Integer tempo, UnidadeTempo unidadeTempo, LocalDateTime ultimaExecucao) {
		super();
		this.name = name;
		this.ativo = ativo;
		this.tempo = tempo;
		this.unidadeTempo = unidadeTempo;
		this.ultimaExecucao = ultimaExecucao;
	}

	public Job(JobDTO dto) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

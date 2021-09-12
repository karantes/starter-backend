package br.com.starter.admin.entity;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.starter.admin.dto.IntegracaoDTO;
import br.com.util.UtilReflection;

@Entity
@Table(name = "integracoes")
public class Integracao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tx_tipo")
	@Enumerated(EnumType.STRING)
	private TipoParametro tipo;

	@Column(name = "tx_informacao")
	private String informacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoParametro getTipo() {
		return tipo;
	}

	public void setTipo(TipoParametro tipo) {
		this.tipo = tipo;
	}

	public String getInformacao() {
		return informacao;
	}

	public void setInformacao(String informacao) {
		this.informacao = informacao;
	}

	/**
	 * @param tipo
	 * @param informacao
	 */
	public Integracao(TipoParametro tipo, String informacao) {
		super();
		this.tipo = tipo;
		this.informacao = informacao;
	}

	public Integracao(IntegracaoDTO dto) {
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

	public Integracao() {
	}

}

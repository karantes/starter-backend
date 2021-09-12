package br.com.starter.admin.dto;

import java.lang.reflect.Field;

import br.com.starter.admin.entity.Integracao;
import br.com.starter.admin.entity.TipoParametro;
import br.com.starter.config.DTO;
import br.com.util.UtilReflection;

public class IntegracaoDTO extends DTO {

	private Long id;

	private TipoParametro tipo;

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

	public IntegracaoDTO(Integracao entity) {
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

	public IntegracaoDTO() {
	}
}

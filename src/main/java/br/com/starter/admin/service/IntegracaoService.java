package br.com.starter.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.starter.admin.dto.IntegracaoDTO;
import br.com.starter.admin.entity.Integracao;
import br.com.starter.admin.entity.TipoParametro;
import br.com.starter.admin.repository.IntegracaoRepository;
import br.com.starter.config.DTO;
import br.com.starter.config.ServiceDTO;
import br.com.starter.config.query.GenericSpesification;
import br.com.starter.config.query.Request;

@Service
public class IntegracaoService implements ServiceDTO<IntegracaoDTO> {

	@Autowired
	private IntegracaoRepository repository;

	@Override
	public Page<DTO> findAll(Request request) {
		return repository.findAll(new GenericSpesification<>(request.getList()), request.getPageable())
				.map(p -> new IntegracaoDTO(p));
	}

	@Override
	public DTO save(IntegracaoDTO dto) {
		return new IntegracaoDTO(repository.save(new Integracao(dto)));
	}

	@Override
	public DTO findById(long id) {
		return new IntegracaoDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

	public IntegracaoDTO findByTipo(TipoParametro tipo) {
		return new IntegracaoDTO(repository.findByTipo(tipo));
	}

	public void iniciaConfiguracaoEmail() {
		if (repository.findByTipo(TipoParametro.HOST_EMAIL) == null)
			repository.save(new Integracao(TipoParametro.HOST_EMAIL, ""));

		if (repository.findByTipo(TipoParametro.PASSWORD_EMAIL) == null)
			repository.save(new Integracao(TipoParametro.PASSWORD_EMAIL, ""));

		if (repository.findByTipo(TipoParametro.PORT_EMAIL) == null)
			repository.save(new Integracao(TipoParametro.PORT_EMAIL, ""));

		if (repository.findByTipo(TipoParametro.USERNAME_EMAIL) == null)
			repository.save(new Integracao(TipoParametro.USERNAME_EMAIL, ""));
	}
}

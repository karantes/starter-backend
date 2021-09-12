package br.com.starter.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.starter.admin.dto.LogDTO;
import br.com.starter.admin.entity.Log;
import br.com.starter.admin.repository.LogRepository;
import br.com.starter.config.DTO;
import br.com.starter.config.ServiceDTO;
import br.com.starter.config.query.GenericSpesification;
import br.com.starter.config.query.Request;

@Service
public class LogService implements ServiceDTO<LogDTO> {

	@Autowired
	private LogRepository repository;

	@Override
	public Page<DTO> findAll(Request request) {
		return repository.findAll(new GenericSpesification<>(request.getList()), request.getPageable())
				.map(p -> new LogDTO(p));
	}

	@Override
	public DTO save(LogDTO dto) {
		return new LogDTO(repository.save(new Log(dto)));
	}

	@Override
	public DTO findById(long id) {
		return new LogDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

}

package br.com.starter.config;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import br.com.starter.config.query.Request;

public interface ServiceDTO<T extends DTO> {

	@Transactional(readOnly = true)
	Page<DTO> findAll(Request request);

	@Transactional
	DTO save(T dto);

	@Transactional(readOnly = true)
	DTO findById(long id);

	@Transactional
	void delete(long id);
}

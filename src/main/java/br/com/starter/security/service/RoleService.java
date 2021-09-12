package br.com.starter.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.starter.config.DTO;
import br.com.starter.config.ServiceDTO;
import br.com.starter.config.query.GenericSpesification;
import br.com.starter.config.query.Request;
import br.com.starter.security.dto.RoleDTO;
import br.com.starter.security.entity.Role;
import br.com.starter.security.repository.RoleRepository;

@Service
public class RoleService implements ServiceDTO<RoleDTO> {

	@Autowired
	private RoleRepository repository;

	@Override
	public Page<DTO> findAll(Request request) {
		return repository.findAll(new GenericSpesification<>(request.getList()), request.getPageable())
				.map(p -> new RoleDTO(p));
	}

	@Override
	public DTO save(RoleDTO dto) {
		return new RoleDTO(repository.save(new Role(dto)));
	}

	@Override
	public DTO findById(long id) {
		return new RoleDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}
}

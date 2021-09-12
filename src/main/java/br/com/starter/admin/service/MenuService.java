package br.com.starter.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.starter.admin.dto.MenuDTO;
import br.com.starter.admin.entity.Menu;
import br.com.starter.admin.repository.MenuRepository;
import br.com.starter.config.DTO;
import br.com.starter.config.ServiceDTO;
import br.com.starter.config.query.GenericSpesification;
import br.com.starter.config.query.Request;

@Service
public class MenuService implements ServiceDTO<MenuDTO> {
	
	@Autowired
	private MenuRepository repository;

	@Override
	public Page<DTO> findAll(Request request) {
		return repository.findAll(new GenericSpesification<>(request.getList()), request.getPageable())
				.map(p -> new MenuDTO(p));
	}

	@Override
	public DTO save(MenuDTO dto) {
		return new MenuDTO(repository.save(new Menu(dto)));
	}

	@Override
	public DTO findById(long id) {
		return new MenuDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}
}

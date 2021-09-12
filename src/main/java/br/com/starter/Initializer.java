package br.com.starter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.starter.admin.service.IntegracaoService;
import br.com.starter.admin.service.JobService;
import br.com.starter.security.dto.UserDTO;
import br.com.starter.security.entity.User;
import br.com.starter.security.service.UserService;

@Service
public class Initializer {

	@Autowired
	private JobService jobService;

	@Autowired
	private UserService userService;

	@Autowired
	private IntegracaoService integracaoService;

	@Autowired
	private PasswordEncoder encoder;

	@PostConstruct
	public void postConstruct() {
		
		userService.save(new UserDTO(new User("Kaique", "04015926188", "kaiquearantes@hotmail.com", encoder.encode("123"))));
		
		jobService.incluirJobs();
		jobService.agendarJobs();

		integracaoService.iniciaConfiguracaoEmail();
	}
}

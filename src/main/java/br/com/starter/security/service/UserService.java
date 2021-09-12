package br.com.starter.security.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.starter.config.DTO;
import br.com.starter.config.ServiceDTO;
import br.com.starter.config.query.GenericSpesification;
import br.com.starter.config.query.Request;
import br.com.starter.security.config.JwtTokenProvider;
import br.com.starter.security.dto.UserDTO;
import br.com.starter.security.entity.User;
import br.com.starter.security.repository.UserRepository;
import br.com.util.Mail;

@Service
public class UserService implements ServiceDTO<UserDTO> {

	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Mail mail;

	@Override
	public Page<DTO> findAll(Request request) {
		return repository.findAll(new GenericSpesification<>(request.getList()), request.getPageable())
				.map(p -> new UserDTO(p));
	}

	@Override
	public DTO save(UserDTO dto) {
		return new UserDTO(repository.save(new User(dto)));
	}

	@Override
	public DTO findById(long id) {
		return new UserDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

	public String signin(UserDTO dto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsernameOrEmail(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = repository.findByUsernameOrEmail(dto.getUsernameOrEmail(), dto.getUsernameOrEmail()).get();
		String jwt = tokenProvider.generateToken(authentication);
		user.setToken(jwt);

		repository.save(user);

		return jwt;
	}

	public Boolean alterPassword(UserDTO dto) {
		try {
			User user = null;
			boolean passwordOK = false;
			if (dto.isPrimeiroLogin()) {
				user = repository.findByToken(dto.getToken());
				passwordOK = dto.getPassword().equals(user.getPassword());

				if (!passwordOK)
					return false;

				user.setPassword(passwordEncoder.encode(dto.getPassword()));
				user.setPrimeiroLogin(false);
				repository.save(user);
				return true;
			} else {
				user = repository.findByHashAlterarSenha(dto.getHashAlterarSenha());
				passwordOK = dto.getPassword().equals(user.getPassword());

				if (!passwordOK)
					return false;

				user.setPassword(passwordEncoder.encode(dto.getPassword()));
				user.setHashAlterarSenha(null);
				repository.save(user);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void enviaEmailRecuperacaoSenha(UserDTO dto) throws UnknownHostException {
		User user = repository.findByUsernameOrEmail(dto.getUsernameOrEmail(), dto.getUsernameOrEmail()).get();
		user.setHashAlterarSenha(String.valueOf((user.getUsername() + user.getId()).hashCode()));
		repository.save(user);

		mail.sendMailPasswordRecovery(user, user.getHashAlterarSenha(), InetAddress.getLocalHost().getHostAddress());
	}

	public void criaNovoUsuario(UserDTO dto) throws Exception {
		User user = new User(dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPrimeiroLogin(true);

		repository.save(user);

	}
}

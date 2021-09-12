package br.com.starter.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.starter.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);

	User findByToken(String token);

	User findByHashAlterarSenha(String hashAlterarSenha);

}

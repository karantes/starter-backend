package br.com.starter.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.starter.admin.entity.Integracao;
import br.com.starter.admin.entity.TipoParametro;

@Repository
public interface IntegracaoRepository extends JpaRepository<Integracao, Long>, JpaSpecificationExecutor<Integracao> {

	Integracao findByTipo(TipoParametro hostEmail);

}

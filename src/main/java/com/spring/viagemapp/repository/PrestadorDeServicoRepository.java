package com.spring.viagemapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;
import com.spring.viagemapp.model.PrestadorDeServico;

@NoRepositoryBean
public interface PrestadorDeServicoRepository<T extends PrestadorDeServico> extends JpaRepository<T, Long>
{
	
	boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByNome(String nome);
    
    Optional<T> findByNomeUsuario(String nomeUsuario);
}

package com.vivahlinda.salesmanagement.repository;

import com.vivahlinda.salesmanagement.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmailId(@Param("email") String email);
}

package com.vivahlinda.salesmanagement.repository;

import com.vivahlinda.salesmanagement.domain.Usuario;
import com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmailId(@Param("email") String email);

    List<UsuarioDTO> findAllUsuario();
    List<UsuarioDTO> findAllAdmin();
}

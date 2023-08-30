package com.vivahlinda.salesmanagement.repository;

import com.vivahlinda.salesmanagement.domain.Usuario;
import com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmailId(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.isAtivo = :isAtivo WHERE u.id = :id")
    Integer updateStatus(@Param("isAtivo") String isAtivo, @Param("id") Integer id);


    List<String> getAllAdmin();


    List<UsuarioDTO> findAllUsuario();
    List<UsuarioDTO> findAllAdmin();
}

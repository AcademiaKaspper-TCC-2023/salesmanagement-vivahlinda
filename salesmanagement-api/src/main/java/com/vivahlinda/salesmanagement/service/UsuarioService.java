package com.vivahlinda.salesmanagement.service;

import com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UsuarioService {
    ResponseEntity<String> inscrever(Map<String, String> requestMap);

    ResponseEntity<String> entrar(Map<String, String> requestMap);

    ResponseEntity<List<UsuarioDTO>> findAllUsuario();

    ResponseEntity<List<UsuarioDTO>> findAllAdmin();

    ResponseEntity<List<UsuarioDTO>> buscarTodos();

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> alterarSenha(Map<String, String> requestMap);

    ResponseEntity<String> recuperarSenha(Map<String, String> requestMap);
}

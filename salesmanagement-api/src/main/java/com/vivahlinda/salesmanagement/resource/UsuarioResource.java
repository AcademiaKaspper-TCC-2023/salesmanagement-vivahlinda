package com.vivahlinda.salesmanagement.resource;

import com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/usuario")
public interface UsuarioResource {

    @PostMapping("/inscrever")
    public ResponseEntity<String> inscrever(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping("/entrar")
    public ResponseEntity<String> entrar(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/findAllUsuario")
    public ResponseEntity<List<UsuarioDTO>> findAllUsuario();

    @GetMapping(path = "/findAllAdmin")
    public ResponseEntity<List<UsuarioDTO>> findAllAdmin();

    @GetMapping(path = "/get")
    public ResponseEntity<List<UsuarioDTO>> buscarTodos();

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

}

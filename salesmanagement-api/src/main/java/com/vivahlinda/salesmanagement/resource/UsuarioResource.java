package com.vivahlinda.salesmanagement.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/usuario")
public interface UsuarioResource {

    @PostMapping("/inscrever")
    public ResponseEntity<String> inscrever(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping("/entrar")
    public ResponseEntity<String> entrar(@RequestBody(required = true) Map<String, String> requestMap);
}

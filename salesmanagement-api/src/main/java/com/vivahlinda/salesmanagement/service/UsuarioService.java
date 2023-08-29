package com.vivahlinda.salesmanagement.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UsuarioService {
    ResponseEntity<String> inscrever(Map<String, String> requestMap);

}

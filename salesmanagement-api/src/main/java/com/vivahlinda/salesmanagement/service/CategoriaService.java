package com.vivahlinda.salesmanagement.service;

import com.vivahlinda.salesmanagement.domain.Categoria;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoriaService {
    ResponseEntity<String> addCategoria(Map<String, String> requestMap);

    ResponseEntity<List<Categoria>> getAllCategoria(String filterValue);

    ResponseEntity<String> updateCategoria(Map<String, String> requestMap);
}

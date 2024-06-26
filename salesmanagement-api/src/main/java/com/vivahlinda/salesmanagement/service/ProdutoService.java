package com.vivahlinda.salesmanagement.service;

import com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProdutoService {
    ResponseEntity<String> addNewProdruto(Map<String, String> requestMap);

    ResponseEntity<List<ProdutoDTO>> getAllProduto();

    ResponseEntity<String> updateProdruto(Map<String, String> requestMap);

    ResponseEntity<String> deleteProduto(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<ProdutoDTO>> getByCategoria(Integer id);

    ResponseEntity<ProdutoDTO> getProdutoById(Integer id);
}

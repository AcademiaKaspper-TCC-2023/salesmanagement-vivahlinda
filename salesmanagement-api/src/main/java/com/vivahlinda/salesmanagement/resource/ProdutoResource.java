package com.vivahlinda.salesmanagement.resource;

import com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/produto")
public interface ProdutoResource {

    @PostMapping(path = "/addNewProdruto")
    ResponseEntity<String> addNewProdruto(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getAllProduto")
    ResponseEntity<List<ProdutoDTO>> getAllProduto();

    @PostMapping(path = "/updateProdruto")
    ResponseEntity<String> updateProdruto(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteProduto(@PathVariable Integer id);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getByCategoria/{id}")
    ResponseEntity<List<ProdutoDTO>> getByCategoria(@PathVariable Integer id);

    @GetMapping(path = "/getProdutoById/{id}")
    ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Integer id);
}

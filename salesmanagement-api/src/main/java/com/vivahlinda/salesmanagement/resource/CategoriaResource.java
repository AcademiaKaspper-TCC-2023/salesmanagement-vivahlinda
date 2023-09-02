package com.vivahlinda.salesmanagement.resource;

import com.vivahlinda.salesmanagement.domain.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/categoria")
public interface CategoriaResource {

    @PostMapping(path = "/addCategoria")
    ResponseEntity<String> addCategoria(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/getAllCategoria")
    ResponseEntity<List<Categoria>> getAllCategoria(@RequestParam(name = "filterValue", required = false) String filterValue);

    @PostMapping(path = "/updateCategoria")
    ResponseEntity<String> updateCategoria(@RequestBody(required = true) Map<String, String> requestMap);
}

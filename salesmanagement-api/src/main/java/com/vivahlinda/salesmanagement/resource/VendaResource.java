package com.vivahlinda.salesmanagement.resource;

import com.vivahlinda.salesmanagement.domain.Venda;
import com.vivahlinda.salesmanagement.domain.dtos.VendaMensalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/venda")
public interface VendaResource {

    @PostMapping(path = "/gerarRelatorio")
    ResponseEntity<String> gerarRelatorio(@RequestBody Map<String, Object> requestMap);

    @GetMapping(path = "/getVenda")
    ResponseEntity<List<Venda>> getVenda();

    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteVenda(@PathVariable Integer id);

    @GetMapping(path = "/vendasMensais")
    ResponseEntity<List<VendaMensalDTO>> getVendasMensais();
}

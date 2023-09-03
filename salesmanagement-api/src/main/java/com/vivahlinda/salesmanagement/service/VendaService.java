package com.vivahlinda.salesmanagement.service;

import com.vivahlinda.salesmanagement.domain.Venda;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface VendaService {

    ResponseEntity<String> gerarRelatorio(Map<String, Object> requestMap);

    ResponseEntity<List<Venda>> getVenda();

    ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

    ResponseEntity<String> deleteVenda(Integer id);
}

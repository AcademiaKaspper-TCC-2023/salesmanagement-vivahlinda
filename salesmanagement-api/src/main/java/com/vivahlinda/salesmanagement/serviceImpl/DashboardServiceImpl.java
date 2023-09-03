package com.vivahlinda.salesmanagement.serviceImpl;

import com.vivahlinda.salesmanagement.repository.CategoriaRepository;
import com.vivahlinda.salesmanagement.repository.ProdutoRepository;
import com.vivahlinda.salesmanagement.repository.UsuarioRepository;
import com.vivahlinda.salesmanagement.repository.VendaRepository;
import com.vivahlinda.salesmanagement.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getContagem() {
        Map<String, Object> map = new HashMap<>();
        map.put("categoria", categoriaRepository.count());
        map.put("produto", produtoRepository.count());
        map.put("venda", vendaRepository.count());
        map.put("usuario", usuarioRepository.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

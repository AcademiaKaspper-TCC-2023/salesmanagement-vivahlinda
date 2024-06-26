package com.vivahlinda.salesmanagement.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/dashboard")
public interface DashboardResource {

    @GetMapping(path = "/detalhes")
    ResponseEntity<Map<String, Object>> getContagem();
}

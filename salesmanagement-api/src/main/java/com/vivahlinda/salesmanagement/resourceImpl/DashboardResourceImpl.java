package com.vivahlinda.salesmanagement.resourceImpl;

import com.vivahlinda.salesmanagement.resource.DashboardResource;
import com.vivahlinda.salesmanagement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardResourceImpl implements DashboardResource {

    @Autowired
    DashboardService dashboardService;

    @Override
    public ResponseEntity<Map<String, Object>> getContagem() {
        return dashboardService.getContagem();
    }
}

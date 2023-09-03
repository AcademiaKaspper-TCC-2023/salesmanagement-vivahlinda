package com.vivahlinda.salesmanagement.resourceImpl;

import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.Venda;
import com.vivahlinda.salesmanagement.resource.VendaResource;
import com.vivahlinda.salesmanagement.service.VendaService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class VendaResourceImpl implements VendaResource {

    @Autowired
    VendaService vendaService;

    @Override
    public ResponseEntity<String> gerarRelatorio(Map<String, Object> requestMap) {
        try {
            return vendaService.gerarRelatorio(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Venda>> getVenda() {
        try {
            return vendaService.getVenda();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            return vendaService.getPdf(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteVenda(Integer id) {
        try {
            return vendaService.deleteVenda(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

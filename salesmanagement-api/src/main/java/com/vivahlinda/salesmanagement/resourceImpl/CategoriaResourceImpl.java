package com.vivahlinda.salesmanagement.resourceImpl;

import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.Categoria;
import com.vivahlinda.salesmanagement.resource.CategoriaResource;
import com.vivahlinda.salesmanagement.service.CategoriaService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoriaResourceImpl implements CategoriaResource {

    @Autowired
    CategoriaService categoriaService;

    @Override
    public ResponseEntity<String> addCategoria(Map<String, String> requestMap) {
        try {
            return categoriaService.addCategoria(requestMap);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Categoria>> getAllCategoria(String filterValue) {
        try {
            return categoriaService.getAllCategoria(filterValue);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategoria(Map<String, String> requestMap) {
        try {
            return categoriaService.updateCategoria(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

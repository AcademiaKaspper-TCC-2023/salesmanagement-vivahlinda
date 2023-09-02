package com.vivahlinda.salesmanagement.resourceImpl;

import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO;
import com.vivahlinda.salesmanagement.resource.ProdutoResource;
import com.vivahlinda.salesmanagement.service.ProdutoService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProdutoResourceImpl implements ProdutoResource {

    @Autowired
    ProdutoService produtoService;


    @Override
    public ResponseEntity<String> addNewProdruto(Map<String, String> requestMap) {
        try {
            return produtoService.addNewProdruto(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProdutoDTO>> getAllProduto() {
        try {
            return produtoService.getAllProduto();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProdruto(Map<String, String> requestMap) {
        try {
            return produtoService.updateProdruto(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduto(Integer id) {
        try {
            return produtoService.deleteProduto(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            return produtoService.updateStatus(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProdutoDTO>> getByCategoria(Integer id) {
        try {
            return produtoService.getByCategoria(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProdutoDTO> getProdutoById(Integer id) {
        try {
            return produtoService.getProdutoById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ProdutoDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

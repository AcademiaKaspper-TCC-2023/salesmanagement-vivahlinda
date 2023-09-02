package com.vivahlinda.salesmanagement.serviceImpl;

import com.google.common.base.Strings;
import com.vivahlinda.salesmanagement.JWT.JwtFilter;
import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.Categoria;
import com.vivahlinda.salesmanagement.repository.CategoriaRepository;
import com.vivahlinda.salesmanagement.service.CategoriaService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addCategoria(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoriaMap(requestMap, false)) {
                    categoriaRepository.save(getCategoriaFromMap(requestMap, false));
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.CATEGORIA_ADD, HttpStatus.OK);
                }
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Categoria>> getAllCategoria(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("Entrou no if do metodo getAllCategoria");
                return new ResponseEntity<List<Categoria>>(categoriaRepository.getAllCategoria(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoriaRepository.findAll(), HttpStatus.OK);

        } catch (Exception exception) {
            return new ResponseEntity<List<Categoria>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateCategoria(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoriaMap(requestMap, true)) {
                    Optional optional = categoriaRepository.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        categoriaRepository.save(getCategoriaFromMap(requestMap, true));
                        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.CATEGORIA_UPDATE_SUCESSO, HttpStatus.OK);
                    } else {
                        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.CATEGORIA_NAO_EXISTE, HttpStatus.OK);
                    }
                }
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.DADOS_INVALIDOS, HttpStatus.BAD_REQUEST);
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoriaMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("nome") && validateId) {
            return true;
        } else if (!validateId) {
            return true;
        }
        return false;
    }

    private Categoria getCategoriaFromMap(Map<String, String> requestMap, Boolean isAdd) {
        Categoria categoria = new Categoria();
        if (isAdd) {
            categoria.setId(Integer.parseInt(requestMap.get("id")));
        }
        categoria.setNome(requestMap.get("nome"));
        return categoria;
    }
}

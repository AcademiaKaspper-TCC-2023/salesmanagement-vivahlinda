package com.vivahlinda.salesmanagement.resourceImpl;

import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO;
import com.vivahlinda.salesmanagement.resource.UsuarioResource;
import com.vivahlinda.salesmanagement.service.UsuarioService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UsuarioResourceImpl implements UsuarioResource {
    @Autowired
    UsuarioService usuarioService;

    @Override
    public ResponseEntity<String> inscrever(Map<String, String> requestMap) {
        try {
            return usuarioService.inscrever(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> entrar(Map<String, String> requestMap) {
        try {
            return usuarioService.entrar(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> findAllUsuario() {
        try {
            return usuarioService.findAllUsuario();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<List<UsuarioDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> findAllAdmin() {
        try {
            return usuarioService.findAllAdmin();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<List<UsuarioDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> buscarTodos() {
        try {
            return usuarioService.buscarTodos();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<List<UsuarioDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return usuarioService.update(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

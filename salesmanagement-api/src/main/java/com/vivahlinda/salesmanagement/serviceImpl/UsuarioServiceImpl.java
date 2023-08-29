package com.vivahlinda.salesmanagement.serviceImpl;

import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.Usuario;
import com.vivahlinda.salesmanagement.repository.UsuarioRepository;
import com.vivahlinda.salesmanagement.service.UsuarioService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<String> inscrever(Map<String, String> requestMap) {
        log.info("Dentro do cadastro {}", requestMap);

        try {
            if (validateIncreverMap(requestMap)) {
                Usuario usuario = usuarioRepository.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(usuario)) {
                    usuarioRepository.save(getUsuarioFromMap(requestMap));
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.SUCESSO_REGISTRO, HttpStatus.OK);
                } else {
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.EMAIL_INVALIDO, HttpStatus.BAD_REQUEST);
                }
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.DADOS_INVALIDOS, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private boolean validateIncreverMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("nome") && requestMap.containsKey("numeroContato")
                && requestMap.containsKey("email") && requestMap.containsKey("senha")
                && requestMap.containsKey("endereco") && requestMap.containsKey("dataNascimento")) {
            return true;
        } else {
            return false;
        }
    }


    private Usuario getUsuarioFromMap(Map<String, String> requestMap) {
        Usuario usuario = new Usuario();
        usuario.setNome(requestMap.get("nome"));
        usuario.setNumeroContato(requestMap.get("numeroContato"));
        usuario.setEndereco(requestMap.get("endereco"));
        usuario.setEmail(requestMap.get("email"));
        usuario.setSenha(requestMap.get("senha"));
        usuario.setIsAtivo("false");
        usuario.setRole("usuario");
        usuario.setDataCriacao(LocalDateTime.now());

        LocalDate dataNascimento = VivahLindaUtils.parseDate(requestMap.get("dataNascimento"));
        usuario.setDataNascimento(dataNascimento);

        return usuario;
    }
}

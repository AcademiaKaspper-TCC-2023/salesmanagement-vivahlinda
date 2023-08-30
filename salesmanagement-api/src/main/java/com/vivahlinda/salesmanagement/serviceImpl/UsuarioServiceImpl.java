package com.vivahlinda.salesmanagement.serviceImpl;

import com.vivahlinda.salesmanagement.JWT.CustomerUsersDetailsService;
import com.vivahlinda.salesmanagement.JWT.JwtFilter;
import com.vivahlinda.salesmanagement.JWT.JwtUtil;
import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.Usuario;
import com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO;
import com.vivahlinda.salesmanagement.repository.UsuarioRepository;
import com.vivahlinda.salesmanagement.service.UsuarioService;
import com.vivahlinda.salesmanagement.utils.EmailUtils;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

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

    @Override
    public ResponseEntity<String> entrar(Map<String, String> requestMap) {
        log.info("Método entrar");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("senha"))
            );
            if (auth.isAuthenticated()) {
                if (customerUsersDetailsService.getUsuarioDetail().getIsAtivo().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUsersDetailsService.getUsuarioDetail().getEmail(),
                                    customerUsersDetailsService.getUsuarioDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"mensagem\":\"" + VivahLindaConstants.CONTATE_O_ADM + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception exception) {
            log.error("{}", exception);
        }
        return new ResponseEntity<String>("{\"mensagem\":\"" + VivahLindaConstants.CREDENCIAL_INVALIDA + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> findAllUsuario() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(usuarioRepository.findAllUsuario(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> findAllAdmin() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(usuarioRepository.findAllAdmin(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> buscarTodos() {
        try {
            if (jwtFilter.isAdmin()) {
                List<Usuario> usuarios = usuarioRepository.findAll();
                List<UsuarioDTO> usuariosDTO = new ArrayList<>();

                for (Usuario usuario : usuarios) {
                    UsuarioDTO usuarioDTO = new UsuarioDTO(
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getCpf(),
                            usuario.getNumeroContato(),
                            usuario.getEmail(),
                            usuario.getIsAtivo(),
                            usuario.getRole(),
                            usuario.getEndereco(),
                            usuario.getDataNascimento(),
                            usuario.getDataCriacao()
                    );
                    usuariosDTO.add(usuarioDTO);
                }

                return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Usuario> optional = usuarioRepository.findById(Integer.parseInt(requestMap.get("id")));

                if (!optional.isEmpty()) {
                    usuarioRepository.updateStatus(requestMap.get("isAtivo"), Integer.parseInt(requestMap.get("id")));

                    // Envia e-mail para todos os administradores informando sobre a atualização de isAtivo.
                    sendMailToAllAdmin(requestMap.get("isAtivo"), optional.get().getEmail(), usuarioRepository.getAllAdmin());

                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.SUCESSO_UPDATE_STATUS, HttpStatus.OK);
                } else {
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ID_USUARIO_INEXISTENTE, HttpStatus.OK);
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
    public ResponseEntity<String> checkToken() {
        return VivahLindaUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> alterarSenha(Map<String, String> requestMap) {
        try {
            Usuario usuarioObj = usuarioRepository.findByEmail(jwtFilter.getUsuarioAtual());
            if (!usuarioObj.equals(null)) {
                if (usuarioObj.getSenha().equals(requestMap.get("senhaAntiga"))) {
                    usuarioObj.setSenha(requestMap.get("senhaNova"));
                    usuarioRepository.save(usuarioObj);
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.SENHA_ATUALIZADA, HttpStatus.OK);
                }
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.SENHA_ANTIGA_INCORRETA, HttpStatus.BAD_REQUEST);
            }
            return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {

        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String isAtivo, String usuario, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getUsuarioAtual());
        String assunto;
        String texto;

        if (isAtivo != null && isAtivo.equalsIgnoreCase("true")) {
            // E-mail de aprovação de usuário
            assunto = VivahLindaConstants.ASSUNTO_CONTA_APROVADA;
            texto = "Olá Administrador,\n\n";
            texto += "Gostaríamos de informar que o usuário " + usuario + " foi aprovado por " + jwtFilter.getUsuarioAtual() + ".\n";
            texto += "A partir de agora, o usuário tem acesso ao sistema de gerenciamento de vendas da Vivah Linda Store.\n\n";
        } else {
            // E-mail de desativação de usuário
            assunto = VivahLindaConstants.ASSUNTO_CONTA_DESABILITADA;
            texto = "Prezado Administrador,\n\n";
            texto += "Informamos que o acesso do usuário " + usuario + " foi desabilitado por " + jwtFilter.getUsuarioAtual() + ".\n";
            texto += "O usuário não terá mais permissão para acessar o sistema de gerenciamento de vendas da Vivah Linda Store.\n\n";
        }

        texto += "Você pode verificar os detalhes do usuário e sua atividade na seção de administrador do sistema.\n\n";
        texto += "Atenciosamente,\n";
        texto += "Equipe de Administração do Sistema Vivah Linda Store";

        // Envia um e-mail para todos os administradores com as informações construídas acima.
        emailUtils.sendSimpleMessage(jwtFilter.getUsuarioAtual(), assunto, texto, allAdmin);
    }


    private boolean validateIncreverMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("nome") && requestMap.containsKey("numeroContato")
                && requestMap.containsKey("email") && requestMap.containsKey("senha")
                && requestMap.containsKey("endereco") && requestMap.containsKey("dataNascimento")
                && requestMap.containsKey("cpf")) {
            return true;
        } else {
            return false;
        }
    }

    private Usuario getUsuarioFromMap(Map<String, String> requestMap) {
        Usuario usuario = new Usuario();
        usuario.setNome(requestMap.get("nome"));
        usuario.setNumeroContato(requestMap.get("numeroContato"));
        usuario.setCpf(requestMap.get("cpf"));
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
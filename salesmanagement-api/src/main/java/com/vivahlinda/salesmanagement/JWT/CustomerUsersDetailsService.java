package com.vivahlinda.salesmanagement.JWT;

import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    private com.vivahlinda.salesmanagement.domain.Usuario usuarioDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Dentro de loadUserByUsername {} ", username);
        usuarioDetail = usuarioRepository.findByEmailId(username);
        if (!Objects.isNull(usuarioDetail))
            return new User(usuarioDetail.getEmail(), usuarioDetail.getSenha(), new ArrayList<>());
        else
            throw new UsernameNotFoundException(VivahLindaConstants.USUARIO_NAO_ENCONTRADO);
    }

    public com.vivahlinda.salesmanagement.domain.Usuario getUsuarioDetail() {
        return usuarioDetail;
    }
}

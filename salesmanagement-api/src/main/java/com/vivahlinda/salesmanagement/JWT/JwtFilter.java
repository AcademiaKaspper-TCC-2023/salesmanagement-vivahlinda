package com.vivahlinda.salesmanagement.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUsersDetailsService service;

    Claims claims = null;
    private String nomeUsuario = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getServletPath().matches(("/usuario/entrar|/usuario/recuperarSenha|/usuario/inscrever"))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {

            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                nomeUsuario = jwtUtil.extrairNomeUsuario(token);

                if (nomeUsuario != null) {
                    claims = jwtUtil.extractAllClaims(token);
                    UserDetails userDetails = service.loadUserByUsername(nomeUsuario);

                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                        );
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }


    // Método que verifica se o usuário autenticado é um administrador.
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    // Método que verifica se o usuário autenticado é um usuário comum.
    public boolean isUsuario() {
        return "usuario".equalsIgnoreCase((String) claims.get("role"));
    }

    // Método que retorna o nome do usuário atualmente autenticado.
    public String getUsuarioAtual() {
        return nomeUsuario;
    }
}

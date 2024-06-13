package com.projeto.cadusu.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.cadusu.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

            // Esse método é chamado quando é feita uma tentativa de autenticação
            // Ele lê os dados de usuário (email e senha) do corpo da requisição e os mapeia para um objeto Usuário
            // Autentica o usuário utilizando o gerenciador de autenticação
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            String nome = usuario.getEmail();
            String senha = usuario.getSenha();
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            nome,
                            senha,
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

            // É Chamado quando a autenticação é bem-sucedida
            // Obtém os detalhes do usuário autenticado
            // Gera um token JWT com base nos detalhes do usuário autenticado
            // Adiciona o token JWT à resposta HTTP
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        Usuario usuario = (Usuario) authResult.getPrincipal();
        String token = JwtTokenProvider.geradorToken(userDetails, usuario);
        JwtTokenProvider.addTokenToResponse(response, token);
    }

}
package com.projeto.cadusu.security.jwt;

import com.projeto.cadusu.security.UsuarioDetailsService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UsuarioDetailsService usuarioDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usuarioDetailsService = usuarioDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

            //Método que executa a lógica de filtro para cada requisição
            //Obtém o cabeçalho de autorização da requisição
            //O IF verifica se o cabeçalho está ausente ou não começa com o prefixo do token JWT
            //Se ausente ou não começar com o prefixo, prossegue para o próximo filtro na cadeia
            //Remove o prefixo do token do cabeçalho
            //IF Valida o token JWT e extrai o nome de usuário do token JWT
            //Se o nome de usuário for válido
            //Carrega os detalhes do usuário (como as permissões) do serviço de detalhes do usuário
            //Cria uma autenticação com os detalhes do usuário
            //Define a autenticação no contexto de segurança do Spring
            //E prossegue para o próximo filtro na cadeia
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String header = request.getHeader(JwtTokenProvider.HEADER_STRING);

        if (header == null || !header.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace(JwtTokenProvider.TOKEN_PREFIX, "");

        if (jwtTokenProvider.validateToken(token)) {
            String username = Jwts.parserBuilder()
                    .setSigningKey(jwtTokenProvider.getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (username != null) {
                UserDetails userDetails = usuarioDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}
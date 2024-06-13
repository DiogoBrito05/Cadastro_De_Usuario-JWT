package com.projeto.cadusu.security.jwt;

import com.projeto.cadusu.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
            //Método secretKeyFor para gerar uma chave segura automaticamente
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

            // Prefixo do token
    static final String TOKEN_PREFIX = "Bearer ";

            // Nome do header, onde o token será colocado
    static final String HEADER_STRING = "Authorization";

            //Tempo de expiração do token em milissegundos (30 minutos)
    public static final long EXPIRATION_TIME = 30 * 60 * 1000; // 30 minutos

            // Método que gerar um token JWT com base nos detalhes do usuário
            // Cria um objeto Claims para representar as informações contidas no token
            // Adiciona informações específicas do usuário como as Roles convertendo para lista de strings
            // Cria o token JWT usando o construtor Jwts.builder()
    public static String geradorToken(UserDetails userDetails, Usuario usuario) {
       Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
       claims.put("ID", usuario.getId());
       claims.put("Nome", usuario.getNome());
       claims.put("Roles", usuario.getRole().stream().map(Enum::name).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims) // Define as reivindicações do token
                .setIssuedAt(new Date()) // Define a data de emissão do token como a data atual
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Define a data de expiração do token
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // Assina o token com a chave secreta usando o algoritmo HS512
                .compact(); // Compacta o token em uma string
   }



            //Verifica se o token é valido
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

            // Método para adicionar um token JWT à resposta HTTP
    public static void addTokenToResponse(HttpServletResponse response, String token) {
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

            // Método público para obter a chave
    public static Key getSecretKey() {
        return SECRET_KEY;
    }



            //Aqui atualiza o token e o retorna pelo cookie
            //Veirifica se o token é válido
            //Faz a crição do novo token
            //Define o cookie na resposta
            // Define o tempo de expiração do cookie em segundos
            // Define o caminho do cookie como raiz
            // Retorna o novo token
     public static String refreshJwtToken(String token, HttpServletResponse response) {
        try {
            if (validateToken(token)) {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                String username = claims.getSubject();
                Date now = new Date();
                Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

                String newToken = Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(now)
                        .setExpiration(expiration)
                        .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                        .compact();

                Cookie cookie = new Cookie("refreshToken", newToken);
                cookie.setHttpOnly(true);
                cookie.setMaxAge((int) (EXPIRATION_TIME / 1000));
                cookie.setPath("/");
                response.addCookie(cookie);

                return newToken;
            } else {
                throw new ExpiredJwtException(null, null, "Token expirado");
            }
        } catch (ExpiredJwtException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
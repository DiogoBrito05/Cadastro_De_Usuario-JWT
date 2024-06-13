package com.projeto.cadusu.security.jwt;

public record JwtResponse(String token) {
        //Representa a resposta do tpken JWT
        //Quando um usuário faz login com sucesso, o token JWT é gerado e retornado como parte da resposta para ser usado em requisições subsequentes para autenticação.
}
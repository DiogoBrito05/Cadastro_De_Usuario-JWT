package com.projeto.cadusu.controller;

import com.projeto.cadusu.Repository.UsuarioRepository;
import com.projeto.cadusu.security.UsuarioDetailsService;
import com.projeto.cadusu.security.jwt.JwtResponse;
import com.projeto.cadusu.security.jwt.JwtTokenProvider;
import com.projeto.cadusu.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
public class TokenController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


            //Gera o token
            //Verifica se o email e a senha são validas
            //Gera o token apenas se as credenciais forem validas
    @PostMapping("/login")
    public ResponseEntity<?> gerarToken(@RequestBody Usuario usuario) {
        try {
            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(usuario.getEmail());

            if (userDetails != null && passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword())) {
                Usuario usuarioAutenticado = usuarioRepository.findByEmail(usuario.getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + usuario.getEmail()));

                String token = jwtTokenProvider.geradorToken(userDetails, usuarioAutenticado);
                return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

            //Faz a atualização do token que é retornado pelo cookie
   @PostMapping("/refresh")
   public ResponseEntity<?> refreshJwtToken(@RequestHeader(value="Authorization") String token, HttpServletResponse response) {
        try {
            String refreshedToken = JwtTokenProvider.refreshJwtToken(token.substring(7), response);
            if (refreshedToken != null) {
                return ResponseEntity.ok(new JwtResponse(refreshedToken));
            } else {
                return ResponseEntity.badRequest().body("Token expirado ou inválido");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o token");
        }
   }

}
package com.projeto.cadusu.controller;

import com.projeto.cadusu.model.Usuario;
import com.projeto.cadusu.service.UsuarioService;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


            //Cadastra um usuário
            //Trata exceções de Nome e E-MAIL duplicados
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (TrataExecao.UsuarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


            // Atualiza um usuário
            // Está chamando o serviço de atualização de usuário;
            // Retorna a resposta com o usuário atualizado e o status HTTP 200 (OK);
            // Retorna uma resposta com a mensagem de erro e o status HTTP 404 (NOT_FOUND)
            // Se Usuario não existir, se o nome e o e-mail for igual á de outro cliente
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (TrataExecao.UsuarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

            // Consulta um usuário com base no id
            //Está chamando serviço de consulta de usuário
            //Retorna a resposta com o usuário encontrado e o status HTTP 200 (OK)
            //Retorna uma resposta com a mensagem de erro e o status HTTP 404 (NOT_FOUND) se o usuário não for encotrado
    @GetMapping("/{id}")
    public ResponseEntity<?> consultarUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.consultarUsuario(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (TrataExecao.UsuarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


            //Remove um usuário
            // Está chamando o serviço de remoção de usuário;
            // Retorna uma resposta com a mensagem de sucesso e o status HTTP 200 (OK);
            // Retorna uma resposta com a mensagem de erro e o status HTTP 404 (NOT_FOUND);
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
        try {
            usuarioService.removerUsuario(id);
            return new ResponseEntity<>("Usuário removido com sucesso", HttpStatus.OK);
        } catch (TrataExecao.UsuarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

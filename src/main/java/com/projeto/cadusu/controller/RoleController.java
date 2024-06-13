package com.projeto.cadusu.controller;

import com.projeto.cadusu.model.Role;
import com.projeto.cadusu.model.Usuario;
import com.projeto.cadusu.service.RoleService;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;


            //Adiciona a ROLE para o usuário de desejo do ADMIN
            //Obtém a ROLE do corpo da requisição
            //Converte a ROLE de string para o tipo ENUM ROLE (Isso é para verificar se está sendo passado somente ROLES válidas)
            //Atribui a ROLE ao usuário
            //Retorna o usuário atualizado com nova ROLE
            //Retorna erro de a ROLE não for válida e se o usuário não for encontrado
    @PostMapping("/{id}/atribuir-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atribuindoRoleUsuario(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
            String roleStr = requestBody.get("role");
            if (roleStr == null || roleStr.isEmpty()) {
                return new ResponseEntity<>("O campo 'role' não pode estar vazio", HttpStatus.BAD_REQUEST);
            }

            Role role = Role.valueOf(roleStr);
            Usuario usuarioAtualizado = roleService.atribuirRoleUsuario(id, role);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Role inválida", HttpStatus.BAD_REQUEST);
        } catch (TrataExecao.UsuarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


            //Remove ROLE de um usuário
            // Chama o serviço para remover a role do usuário especificado
            // Retorna uma resposta OK (200) com o usuário atualizado
            //Retorna erro se a role fornecida não for válida e se o usuário não for encontrado
   @DeleteMapping("/{userId}/remove_role/{role}")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<?> removendoRoleUsuario(@PathVariable Long userId, @PathVariable String role) {
        try {
            Usuario usuarioAtualizado = roleService.removerRoleUsuario(userId, role);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Role inválida", HttpStatus.BAD_REQUEST);
        } catch (TrataExecao.UsuarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
   }

}

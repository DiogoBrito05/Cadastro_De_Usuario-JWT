package com.projeto.cadusu.service;

import com.projeto.cadusu.Repository.UsuarioRepository;
import com.projeto.cadusu.model.Role;
import com.projeto.cadusu.model.Usuario;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private UsuarioRepository usuarioRepository;


            // Adiciona uma ROLE a um usuário
    public Usuario atribuirRoleUsuario(Long id, Role role) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.UsuarioException("Usuário não encontrado"));

        Set<Role> roles = usuario.getRole();
        roles.add(role);
        usuario.setRole(roles);

        return usuarioRepository.save(usuario);
    }


            //Remove a role de um usuário
            //Verifica se o usuário é existente e se possui a role que deseja remover
            //Remove a role desejada
            //Atualiza as roles do usuário
    public Usuario removerRoleUsuario(Long id, String roleStr) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.UsuarioException("Usuário não encontrado"));

        Role role = Role.valueOf(roleStr);

        Set<Role> roles = usuario.getRole();

        if (!roles.contains(role)) {
            throw new TrataExecao.UsuarioException("O usuário não possui a role especificada");
        }

        roles.remove(role);
        usuario.setRole(roles);

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioAtualizado;
    }


}

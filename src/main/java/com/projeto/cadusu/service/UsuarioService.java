package com.projeto.cadusu.service;

import com.projeto.cadusu.Repository.UsuarioRepository;
import com.projeto.cadusu.model.Role;
import com.projeto.cadusu.model.Usuario;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


            //Cadastra um usuário novo
            // Verifica se o nome de usuário e o e-mailjá existe
            // Criptografa a senha antes de salvar no banco de dados
            // Adiciona a Role "USER" para o usuário ao fazer o cadastro
            // Salva o usuário no banco de dados
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByNome(usuario.getNome())) {
            throw new TrataExecao.UsuarioException("Nome de usuário já existe");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new TrataExecao.UsuarioException("E-mail já cadastrado. Por favor, escolha outro.");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.getRole().add(Role.ROLE_USER);
        return usuarioRepository.save(usuario);
    }


            //Atualiza o usuário
            //Verifica se o usuário existe
            //Verifica se o usuário está autorizado ou não
            //Veridica se o novo NOME e E-MAIL de usuárioé igual aos que ja existem no banco de dados
    public Usuario atualizarUsuario(Long id, Usuario novoUsuario) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.UsuarioException("Usuário não encontrado"));
        if (!verificaAutenticacao(id)) {
            throw new TrataExecao.UsuarioException("Acesso não autorizado");
        }
        if (!usuarioExistente.getNome().equals(novoUsuario.getNome())
                && usuarioRepository.existsByNome(novoUsuario.getNome())) {
            throw new TrataExecao.UsuarioException("Nome de usuário já existe");
        }
        if (!usuarioExistente.getEmail().equals(novoUsuario.getEmail())
                && usuarioRepository.existsByEmail(novoUsuario.getEmail())) {
            throw new TrataExecao.UsuarioException("E-mail já cadastrado. Por favor, escolha outro.");
        }
        usuarioExistente.setNome(novoUsuario.getNome());
        usuarioExistente.setEmail(novoUsuario.getEmail());
        return usuarioRepository.save(usuarioExistente);
    }

            //Remove o usuário
            //Verifica se o usuário existe e remove o usuário do banco de dados
            //Verifica se o usuário tem permissão para remover
    public void removerUsuario(Long id) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.UsuarioException("Usuário não encontrado"));
        if (!verificaAutenticacao(id)) {
            throw new TrataExecao.UsuarioException("Acesso não autorizado");
        }
        usuarioRepository.delete(usuarioExistente);
    }

            //Consulta o usuário pelo id
            //Retorna o usuário encontrado, caso não for encontrado, lança uma exceção
    public Usuario consultarUsuario(Long id) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.UsuarioException("Usuário não encontrado"));

        if (!verificaAutenticacao(id)) {
            throw new TrataExecao.UsuarioException("Acesso não autorizado");
        }
        return usuarioExistente;
    }

            //Método que verifica se o usuário autenticado tem permissão para acessar o recurso
            //Verifica se o usuário existe ou não
            //Verifica se o usuário autenticado é um administrador ou se é o próprio usuário que está sendo consultado
    public boolean verificaAutenticacao(Long usuarioId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Email do usuário autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new TrataExecao.UsuarioException("Usuário não encontrado"));

        boolean isAdmin = usuario.getRole().contains(Role.ROLE_ADMIN);

        return isAdmin || usuario.getId().equals(usuarioId);
    }
}





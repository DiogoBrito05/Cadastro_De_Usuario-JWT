package com.projeto.cadusu.Repository;

import com.projeto.cadusu.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
            //Verifica se já existe um usuário com o mesmo nome de usuário
    boolean existsByNome(String nome);

            //Verifica se já existe um usuário com o e-mail .
    boolean existsByEmail(String email);


            // Retorna um Optional contendo um usuário cujo e-mail corresponde ao valor fornecido
    Optional<Usuario> findByEmail(String email);



}
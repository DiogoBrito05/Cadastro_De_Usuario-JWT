package com.projeto.cadusu.Repository;

import com.projeto.cadusu.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
            //Verifica se já existe um cliente com o mesmo nome
    boolean existsByNome(String nome);
            //Salva um novo cliente no banco de dados
    Cliente save(Cliente novoCliente);
            //Verifica se já existe um cliente com o mesmo CPF
    boolean existsByCpf(String cpf);

}

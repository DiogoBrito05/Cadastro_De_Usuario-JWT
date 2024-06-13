package com.projeto.cadusu.service;

import com.projeto.cadusu.Repository.ClienteRepository;
import com.projeto.cadusu.Repository.sale.VendaRepository;
import com.projeto.cadusu.model.Cliente;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VendaRepository vendaRepository;

            // Obtém a lista de todos os clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

             // Adiciona um novo cliente e verifica se já existe pelo  CPF
    public Cliente adicionarCliente(Cliente novoCliente) {
        if (clienteRepository.existsByCpf(novoCliente.getCpf())) {
            throw new TrataExecao.ClienteException("CPF de cliente já existe");
        }

        return clienteRepository.save(novoCliente);
    }

            // Busca um cliente pelo id e lança uma exceção se não encontrado
    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ClienteException("Cliente não encontrado"));
    }

            //Atualiza o cliente e verifica se ja existe um cliente com o com mesmo cpf
    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ClienteException("Cliente não encontrado"));

        if (!clienteExistente.getCpf().equals(clienteAtualizado.getCpf()) &&
                clienteRepository.existsByCpf(clienteAtualizado.getCpf())) {
            throw new TrataExecao.ClienteException("CPF de cliente já existe");
        }

            // Atualize os campos necessários do cliente existente com os dados do cliente atualizado.
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());
        clienteExistente.setAtivo(clienteAtualizado.getAtivo());

        return clienteRepository.save(clienteExistente);
    }

            //Remove Cliente pelo id e lança uma exceção se não encontrá-lo
            // Verifica se há vendas cadastradas pelo id do cliente
            // Se houver vendas lança uma exceção retornando uma mensagem
    public void removerCliente(Long id) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ClienteException("Cliente não encontrado"));

        long quantidadeVendas = vendaRepository.countByClienteId(id);
        if (quantidadeVendas > 0) {
            throw new TrataExecao.ClienteException("Cliente possui venda cadastrada. Exclua as vendas cadastradas para prosseguir!");
        }
        clienteRepository.deleteById(id);
    }

}

package com.projeto.cadusu.controller;

import com.projeto.cadusu.model.Cliente;
import com.projeto.cadusu.service.ClienteService;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import com.projeto.cadusu.service.product.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProdutoService produtoService;

            // Listar todos os clientes
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

            //Adiciona um novo cliente
            //Adiciona o cliente e retorna a resposta com o cliente adicionado e status CREATED
            //Trata exceção de CPF de cliente duplicado e retorna a mensagem adequada com status BAD_REQUEST
    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adicionarCliente(@RequestBody Cliente novoCliente) {
        try {
            Cliente clienteAdicionado = clienteService.adicionarCliente(novoCliente);
            return new ResponseEntity<>(clienteAdicionado, HttpStatus.CREATED);
        } catch (TrataExecao.ClienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

             // Busca um cliente pelo id
             // Busca o cliente pelo id e retorna a resposta com o cliente encontrado e status OK
             // Trata exceção de cliente não encontrado e retorna a mensagem adequada com status NOT_FOUND
    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (TrataExecao.ClienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

            //Atualiza um cliente pelo id
            //Atualiza o cliente e retorna a resposta com o cliente atualizado e status OK
            //Trata exceção de cliente não encontrado e retorna a mensagem adequada com status NOT_FOUND
            //Trata a exceção de CPF duplicado retornando a mensagem adequada com status BAD_REQUEST
    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        try {
            Cliente clienteAtualizadoResultado = clienteService.atualizarCliente(id, clienteAtualizado);
            return new ResponseEntity<>(clienteAtualizadoResultado, HttpStatus.OK);
        } catch (TrataExecao.ClienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
             //Remove o cliente pelo id
             //Remove o cliente e retorna a mensagem de sucesso com status OK
             //Trata exceção de cliente não encontrado e retorna a mensagem adequada com status NOT_FOUND
    @DeleteMapping("/remover/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removerCliente(@PathVariable Long id) {
        try {
            clienteService.removerCliente(id);
            return new ResponseEntity<>("Cliente removido com sucesso", HttpStatus.OK);
        } catch (TrataExecao.ClienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
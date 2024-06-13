package com.projeto.cadusu.controller.product;

import com.projeto.cadusu.model.product.Produto;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import com.projeto.cadusu.service.product.CategoriaService;
import com.projeto.cadusu.service.product.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CategoriaService categoriaService;



            //Adiciona um produto
            //Chama do produto para adicionar um novo produto
            //Retorna a resposta com o produto adicionado e o status (201)
            //Trata exceções e retorna a mensagem adequada com o status correspondente
    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adicionarProduto(@RequestBody Produto novoProduto) {
        try {
            Produto produtoAdicionado = produtoService.adicionarProduto(novoProduto);
            return new ResponseEntity<>(produtoAdicionado, HttpStatus.CREATED);
        } catch (TrataExecao.ProdutoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


            //Lista todos os produtos
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<?> listarProdutos(){
        List<Produto> produtos = produtoService.listarProdutos();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }


            //Atualiza um produto pelo id
            // Atualiza o produto e retorna o resultado
            // Trata exceção e retorna a mensagem adequada com o status correspondente
    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        try {
            Produto produtoAtualizadoResultado = produtoService.atualizarProduto(id, produtoAtualizado);
            return new ResponseEntity<>(produtoAtualizadoResultado, HttpStatus.OK);
        } catch (TrataExecao.ProdutoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

            //Remove um produto pelo id
            // Remove o produto e retorna a mensagem de sucesso
            // Trata exceção e retorna a mensagem adequada com o status correspondente
    @DeleteMapping("/remover/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removerProduto(@PathVariable Long id) {
        try {
            produtoService.removerProduto(id);
            return new ResponseEntity<>("Produto removido com sucesso", HttpStatus.OK);
        } catch (TrataExecao.ProdutoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
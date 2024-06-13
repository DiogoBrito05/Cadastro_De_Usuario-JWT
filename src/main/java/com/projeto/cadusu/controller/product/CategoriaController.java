package com.projeto.cadusu.controller.product;

import com.projeto.cadusu.model.product.Categoria;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import com.projeto.cadusu.service.product.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categoria_produto")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

            //Lista todas as categorias e as obtêm
            //Retorna a lista das categorias como resposta HTTP OK(200)
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

            //Adiciona uma nova categoria e restorna a categria recém-adicionada como resposta HTTP CREATED(201)
    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> adicionarCategoria(@RequestBody Categoria novaCategoria) {
        Categoria categoriaAdicionada = categoriaService.adicionarCategoria(novaCategoria);
        return new ResponseEntity<>(categoriaAdicionada, HttpStatus.CREATED);
    }

            //Busca uma categoria pelo id, retorna a categoria encontrada como resposta HTTP OK (200)
    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        Categoria categoriaProduto = categoriaService.buscarCategoriaPorId(id);
        return new ResponseEntity<>(categoriaProduto, HttpStatus.OK);
    }

             //Remove uma categoria pelo id
             //Chama o método do serviço para remover a categoria pelo ID
             //Retorna uma mensagem de sucesso como resposta HTTP OK (200)
             //Trata a exceção quando a categoria não é encontrada
    @DeleteMapping("/remover/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removerCategoriaPorId(@PathVariable Long id) {
        try {
            categoriaService.removerCategoriaPorId(id);
            return new ResponseEntity<>("Categoria removida com sucesso", HttpStatus.OK);
        } catch (TrataExecao.ProdutoException  e) {
            return new ResponseEntity<>("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
    }
}



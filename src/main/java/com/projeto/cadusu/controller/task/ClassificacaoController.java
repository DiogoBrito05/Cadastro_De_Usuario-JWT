package com.projeto.cadusu.controller.task;

import com.projeto.cadusu.model.task.Classificacao;
import com.projeto.cadusu.service.task.ClassificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("classificacao")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

            //Lista todas as classificações
            //Chama o serviço para obter a lista de classificações
            //Retorna a lista de classificações como resposta HTTP OK (200)
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<List<Classificacao>> listarClassificacao() {
        List<Classificacao> classificacao = classificacaoService.classificacao();
        return new ResponseEntity<>(classificacao, HttpStatus.OK);
    }

            //Adiciona uma nova classificações
            //Chama o serviço para adicionar a nova cclassificações
            //Retorna a classificações recém-adicionada como resposta HTTP CREATED (201)
    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Classificacao> adicionarClassificacao(@RequestBody Classificacao novaClassificacao) {
        Classificacao classificacaoAdicionada = classificacaoService.adicionarClassificacao(novaClassificacao);
        return new ResponseEntity<>(classificacaoAdicionada, HttpStatus.CREATED);
    }

            //Busca uma classificação pelo id
            //Chama o serviço para buscar a classificação pelo id
            //Retorna a classificação encontrada como resposta HTTP OK (200)
    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<Classificacao> buscarClassificacaoPorId(@PathVariable Long id) {
        Classificacao classificacao = classificacaoService.buscarClassificacaoPorId(id);
        return new ResponseEntity<>(classificacao, HttpStatus.OK);
    }

}

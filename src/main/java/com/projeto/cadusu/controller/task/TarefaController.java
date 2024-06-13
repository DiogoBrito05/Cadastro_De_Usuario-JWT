package com.projeto.cadusu.controller.task;

import com.projeto.cadusu.model.task.Classificacao;
import com.projeto.cadusu.model.task.Tarefa;
import com.projeto.cadusu.service.task.ClassificacaoService;
import com.projeto.cadusu.service.task.TarefaService;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private ClassificacaoService classificacaoService;


            //Adiciona uma tarefa
            //Adicione uma classificação à tarefa se estiver presente no corpo da requisição
            //Configura a propriedade 'concluido'
    @PostMapping("/{usuarioId}/adicionar-tarefa")
    public ResponseEntity<?> adicionarTarefa(@PathVariable Long usuarioId, @RequestBody Tarefa novaTarefa) {
        try {
            if (novaTarefa.getClassificacao() != null) {
                Classificacao classificacao = classificacaoService.buscarClassificacaoPorId(novaTarefa.getClassificacao().getId());
                novaTarefa.setClassificacao(classificacao);
            }
            novaTarefa.setConcluido(false);

            Tarefa tarefaAdicionada = tarefaService.adicionarTarefa(usuarioId, novaTarefa);
            return new ResponseEntity<>(tarefaAdicionada, HttpStatus.OK);
        } catch (TrataExecao.UsuarioException | TrataExecao.TarefaException |
                 TrataExecao.ClassificacaoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

             //Atualizar uma tarefa de um usuário
             //Chama o serviço para atualizar a tarefa com os campos fornecidos
             //Retorna a tarefa atualizada com sucesso
             //Em caso de exceção, retorna uma resposta de not found
    @PutMapping("/{usuarioId}/atualizar-tarefa/{tarefaId}")
    public ResponseEntity<?> atualizarTarefa(@PathVariable Long usuarioId, @PathVariable Long tarefaId, @RequestBody Map<String, Object> camposAtualizacao) {
        try {
            Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(usuarioId, tarefaId, camposAtualizacao);

            return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
        } catch (TrataExecao.UsuarioException | TrataExecao.TarefaException |
                 TrataExecao.ClassificacaoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
            //Lista todas as tarefas de um usuário
            //Chama o serviço para obter a lista de tarefas associadas ao usuário
            //Retorna a lista de tarefas com sucesso
            //Retorna a mensagem de erro como uma String
    @GetMapping("/{usuarioId}/listar-tarefas")
   public ResponseEntity<?> listarTarefas(@PathVariable Long usuarioId) {
        try {
            List<Tarefa> tarefas = tarefaService.listarTarefasPorUsuario(usuarioId);
            return new ResponseEntity<>(tarefas, HttpStatus.OK);
        } catch (TrataExecao.UsuarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
   }

             // Remove uma tarefa
             //Retorna uma mensagem dizwndo que foi removido com sucesso
             //Em caso de execeção, retorna uma mensgaem de erro e o status NOT_NOUT
    @DeleteMapping("/{usuarioId}/remover-tarefa/{tarefaId}")
    public ResponseEntity<?> removerTarefa(@PathVariable Long usuarioId, @PathVariable Long tarefaId) {
        try {
            tarefaService.removerTarefa(usuarioId, tarefaId);
            return new ResponseEntity<>("Tarefa removida com sucesso", HttpStatus.OK);
        } catch (TrataExecao.UsuarioException | TrataExecao.TarefaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}



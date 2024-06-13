package com.projeto.cadusu.service.task;

import com.projeto.cadusu.Repository.task.TarefaRepository;
import com.projeto.cadusu.Repository.UsuarioRepository;
import com.projeto.cadusu.model.Role;
import com.projeto.cadusu.model.task.Classificacao;
import com.projeto.cadusu.model.task.Tarefa;
import com.projeto.cadusu.model.Usuario;
import com.projeto.cadusu.service.UsuarioService;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClassificacaoService classificacaoService;
    @Autowired
    private UsuarioService usuarioService;


            //Adiciona uma nova tarefa associada a um usuário
            //Verifica se o usuário existe
            //Verifica se o usuário autenticado tem a permissão necessária para adicionar tarefa
            //Associa a tarefa ao usuário
            //Salva a tarefa no banco de dados usando o repositório de tarefas
    public Tarefa adicionarTarefa(Long usuarioId, Tarefa novaTarefa) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new TrataExecao.TarefaException("Usuário não encontrado"));

        if (!usuarioService.verificaAutenticacao(usuarioId)) {
            throw new TrataExecao.UsuarioException("Acesso não autorizado");
        }
        novaTarefa.setUsuario(usuarioExistente);
        tarefaRepository.save(novaTarefa);
        return novaTarefa;
    }

            //Atualiza uma tarefa existente associada a um usuário
            //Verifica se o usuário existe e se aterfa existe
            //Verifica se o usuário autenticado tem a permissão
            //Processa os campos específicos a serem atualizados
            //Salva a tarefa atualizada no banco de dados usando o repositório de tarefas
    public Tarefa atualizarTarefa(Long usuarioId, Long tarefaId, Map<String, Object> camposAtualizacao) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new TrataExecao.TarefaException("Usuário não encontrado"));
        Tarefa tarefaExistente = usuarioExistente.getTarefas().stream()
                .filter(t -> t.getId().equals(tarefaId))
                .findFirst()
                .orElseThrow(() -> new TrataExecao.TarefaException("Tarefa não encontrada"));

        if (!usuarioService.verificaAutenticacao(usuarioId)) {
            throw new TrataExecao.UsuarioException("Acesso não autorizado");
        }

        if (camposAtualizacao.containsKey("descricao")) {
            tarefaExistente.setDescricao((String) camposAtualizacao.get("descricao"));
        }
        if (camposAtualizacao.containsKey("classificacao")) {
            Long classificacaoId = ((Number) camposAtualizacao.get("classificacao")).longValue();
            Classificacao classificacao = classificacaoService.buscarClassificacaoPorId(classificacaoId);
            tarefaExistente.setClassificacao(classificacao);
        }
        if (camposAtualizacao.containsKey("concluido")) {
            tarefaExistente.setConcluido((Boolean) camposAtualizacao.get("concluido"));
        }
        tarefaRepository.save(tarefaExistente);

        return tarefaExistente;
    }


            //Lista as tarefas de um usuário
            // Verifica se o usuário existe
            //Verifica se o usuário tem a permissão
    public List<Tarefa> listarTarefasPorUsuario(Long usuarioId) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new TrataExecao.UsuarioException ("Usuário não encontrado"));

        if (!usuarioService.verificaAutenticacao(usuarioId)) {
            throw new TrataExecao.UsuarioException("Acesso não autorizado");
        }

        return usuarioExistente.getTarefas();
    }

            //Remove uma tarefa associada a um usuário
            //Verifica se o usuário e a tarefa existe
            //Verifica se o usuário tem a permissão
            //Remove a tarefa do usuário
            //Salva o usuário sem a tarefa no banco de dados usando o repositório de usuários
    public void removerTarefa(Long usuarioId, Long tarefaId) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new TrataExecao.TarefaException("Usuário não encontrado"));
        Tarefa tarefaExistente = usuarioExistente.getTarefas().stream()
                .filter(t -> t.getId().equals(tarefaId))
                .findFirst()
                .orElseThrow(() -> new TrataExecao.TarefaException("Tarefa não encontrada"));

        if (!usuarioService.verificaAutenticacao(usuarioId)) {
            throw new TrataExecao.UsuarioException("Acesso não autorizado");
        }
        usuarioExistente.getTarefas().remove(tarefaExistente);
        usuarioRepository.save(usuarioExistente);
    }


}
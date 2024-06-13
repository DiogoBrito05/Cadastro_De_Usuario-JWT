package com.projeto.cadusu.service.task;

import com.projeto.cadusu.Repository.task.ClassificacaoRepository;
import com.projeto.cadusu.model.task.Classificacao;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository classificacaoRepository;



            //Lista todas as classificações
            //Chama o método do repositório para obter todas as classficações
    public List<Classificacao> classificacao() {
        return classificacaoRepository.findAll();
    }

            // Adiciona uma nova classificação
            // Chama o método do repositório e salva a nova classificação
    public Classificacao adicionarClassificacao(Classificacao novaClassificacao) {
        return classificacaoRepository.save(novaClassificacao);
    }

            //Busca uma classificação por ID
            //Chama o método do repositório para buscar a classificação pelo ID
            //Se não encontrada, lança a exceção ClassificacaoNotFoundException de TratarExecao
    public Classificacao buscarClassificacaoPorId(Long id) {
        return classificacaoRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ClassificacaoNotFoundException("Classificacao não encontrada"));
    }
}

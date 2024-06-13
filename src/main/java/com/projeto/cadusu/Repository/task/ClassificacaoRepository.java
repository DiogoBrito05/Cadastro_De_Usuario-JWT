package com.projeto.cadusu.Repository.task;

import com.projeto.cadusu.model.task.Classificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

}

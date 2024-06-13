package com.projeto.cadusu.model.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projeto.cadusu.model.Usuario;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

            //Descreve a tarefa
    @NotBlank(message = "O campo 'descricao' é obrigatório.")
    private String descricao;

            // Indica se a tarefa está concluída ou não
    private boolean concluido;

            // Relacionamento muitos-para-um com a entidade de usuário e categoria
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "classificacao_id")
    private Classificacao classificacao;


    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

                //Setter para atribuir a categoria da tarefa
    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }

                //Getter para obter a categoria da tarefa
    public Classificacao getClassificacao() {
        return classificacao;
    }

                //Getter para obter o ID da tarefa
    public Long getId() {
        return id;
    }

                //Setter para atribuir o ID da tarefa
    public void setId(Long id) {
        this.id = id;
    }
                 //Atribui a descrição da tarefa
    public String getDescricao() {
        return descricao;
    }

                  //Setter para atribuir a descrição da tarefa
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

                   //Obtem o usuário associado à tarefa
    public Usuario getUsuario() {
        return usuario;
    }

                    // Atribui o usuário associado à tarefa
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override       //Método hashCode para gerar um código de hash único para a tarefa
    public int hashCode() {
        return Objects.hash(id, descricao, usuario);
    }

    @Override       // comparara duas tarefas e verificar se são iguais
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tarefa task = (Tarefa) obj;
        return Objects.equals(id, task.id) &&
                Objects.equals(descricao, task.descricao) &&
                Objects.equals(usuario, task.usuario);
    }
}


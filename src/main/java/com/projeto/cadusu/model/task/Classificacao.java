package com.projeto.cadusu.model.task;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Classificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull      // Nome da cclassificação, não pode ser nulo e deve ter entre 3 e 20 caracteres
    @Size(min = 3, max = 20)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override     // Sobrescrita do método equals para comparar objetos Classificacao
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classificacao categoria = (Classificacao) o;
        return Objects.equals(id, categoria.id);
    }

    @Override       // Método hashCode para gerar um código de hash baseado no id
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.projeto.cadusu.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank (message = "Nome do 'cliente' é obrigatório")  // Nome do cliente é obrigatório
    private String nome;
    @NotBlank  (message = "CPF do 'cliente' é obrigatório") // CPF do cliente é obrigatório
    private String cpf;
    @NotNull  (message = "Endereço do 'cliente' é obrigatório")
    @Embedded  // Endereço do cliente, armazenado como uma entidade incorporada
    private Endereco endereco;
    @NotNull (message = "Você deve informar se o cliente é 'ATIVO' ou não." )
    private Boolean ativo;

    //Construtor padrão
    public Cliente(){

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

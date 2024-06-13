package com.projeto.cadusu.model.sale;

import com.projeto.cadusu.model.Cliente;
import com.projeto.cadusu.model.product.Produto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull //Valor da venda não pode ser nulo
    private BigDecimal valor;

    @NotNull//Não pode ser nulo
    private LocalDate dataCompra;

    @NotNull //Não pode ser nulo
    private LocalDate dataVencimento;

    @NotNull //Não pode ser nulo e tem a precisão total de 5 dígitos e com 2 reservaods para o decimal
    @Column(precision = 5, scale = 2)
    private BigDecimal desconto;

    @NotNull //Não pode ser nulo
    @Column(nullable = false)
    private int quantidade;

    // Relacionamento muitos-para-um com a entidade Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    // Relacionamento muitos-para-um com a entidade Produto
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;


    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

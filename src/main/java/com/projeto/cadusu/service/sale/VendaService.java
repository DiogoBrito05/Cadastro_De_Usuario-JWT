package com.projeto.cadusu.service.sale;

import com.projeto.cadusu.Repository.ClienteRepository;
import com.projeto.cadusu.Repository.product.ProdutoRepository;
import com.projeto.cadusu.Repository.sale.VendaRepository;
import com.projeto.cadusu.model.Cliente;
import com.projeto.cadusu.model.product.Produto;
import com.projeto.cadusu.model.sale.Venda;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

            // Adiciona uma nova venda no sistema
            // Valida os dados da venda antes de processar
            // Busca informações do cliente associado à venda
            // Busca informações do produto associado à venda
            // Calcula o preço da venda antes de persistir
            // Salva a venda no banco de dados
    public Venda adicionarVenda(Venda novaVenda, Double desconto) {
        validarDadosVenda(novaVenda);

        Cliente cliente = clienteRepository.findById(novaVenda.getCliente().getId())
                .orElseThrow(() -> new TrataExecao.ClienteException("Cliente não encontrado com o ID: " + novaVenda.getCliente().getId()));

        Produto produto = produtoRepository.findById(novaVenda.getProduto().getId())
                .orElseThrow(() -> new TrataExecao.ProdutoException("Produto não encontrado com o ID: " + novaVenda.getProduto().getId()));

           // Verifica se a quantidade é maior que zero
        if (novaVenda.getQuantidade() <= 0) {
            throw new TrataExecao.VendaException("O Campo 'quantidade' não deve ser zero!");
        }
           // Defini informações do cliente e produto na venda para não sair como null
        novaVenda.setCliente(cliente);
        novaVenda.setProduto(produto);

        calcularPrecoVenda(novaVenda);

        vendaRepository.save(novaVenda);

        return novaVenda;
    }


            //Esse método ele foi criado para validar os dados da venda
            //Nele você pode verifica se as datas foram informadas e se a data da compra é anterior á data de vencimento
            //Se o cliente existe e se o produto também é existente
    private void validarDadosVenda(Venda venda) {
        if (venda.getDataCompra() == null || venda.getDataVencimento() == null) {
            throw new TrataExecao.VendaException("A data de compra e a data de vencimento são obrigatórias.");
        }
        if (venda.getDataCompra().isAfter(venda.getDataVencimento())) {
            throw new TrataExecao.VendaException("A data de compra deve ser anterior à data de vencimento.");
        }

        if (!clienteRepository.existsById(venda.getCliente().getId())) {
            throw new TrataExecao.ClienteException("Cliente não encontrado com o ID: " + venda.getCliente().getId());
        }

        if (!produtoRepository.existsById(venda.getProduto().getId())) {
            throw new TrataExecao.ProdutoException("Produto não encontrado com o ID: " + venda.getProduto().getId());
        }
    }

            // Calcula o preço da venda e verifica se o valor foi fornecido e se é maior que zero
    private void calcularPrecoVenda(Venda venda) {
        BigDecimal valorVenda = venda.getValor();

        if (valorVenda == null || valorVenda.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TrataExecao.VendaException("O valor da venda deve ser fornecido e ser maior que zero.");
        }

        venda.setValor(valorVenda.setScale(2, RoundingMode.HALF_UP)); // Define o valor da venda com 2 casas decimais
    }



               //Lista todas as vendas
    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

               //Cancela uma venda com base no id
               //Verificar se a venda com o id fornecido existe
               //Faz a lógica para remoção ou lança uma exceção se não encontrada
    public void cancelarVenda(Long vendaId) {
        Optional<Venda> vendaExistenteOptional = vendaRepository.findById(vendaId);

        vendaExistenteOptional.ifPresentOrElse(
                vendaExistente -> vendaRepository.delete(vendaExistente),
                () -> {
                    throw new TrataExecao.VendaException("Venda com ID " + vendaId + " não encontrada");
                }
        );
    }

            //Consulta uma venda pelo seu id
            //Retorna a venda encontrada, caso não for encontrada, lança uma exceção
    public Venda consultarVenda(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.VendaException ("Venda não encontrada!"));
    }
}
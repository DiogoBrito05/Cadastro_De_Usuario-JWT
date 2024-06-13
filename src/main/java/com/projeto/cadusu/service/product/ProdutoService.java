package com.projeto.cadusu.service.product;

import com.projeto.cadusu.Repository.ClienteRepository;
import com.projeto.cadusu.Repository.product.ProdutoRepository;
import com.projeto.cadusu.Repository.sale.VendaRepository;
import com.projeto.cadusu.model.product.Categoria;
import com.projeto.cadusu.model.product.Produto;
import com.projeto.cadusu.service.ClienteService;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaService categoriaProdutoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VendaRepository vendaRepository;

            //Adicone um produto
            //Verifica se a categoria do novo produto é válida
            //Busca a categoria do produto pelo ID
            //Associa a categoria encontrada ao novo produto
            //Salva o novo produto no repositório e retorna o produto adicionado
    public Produto adicionarProduto(Produto novoProduto) {
        if (novoProduto.getCategoria() != null && novoProduto.getCategoria().getId() != null) {
            Categoria categoriaProduto = categoriaProdutoService.buscarCategoriaPorId(novoProduto.getCategoria().getId());
            novoProduto.setCategoria(categoriaProduto);
        }
        return produtoRepository.save(novoProduto);
    }


            //Lista o Produto
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

            //Atualiza o produto
            // Busca o produto existente pelo ID e se não encontrar o produto, lança uma exceção.
            //Verifica e atualiza a descrição, valor, categoria e unidade de medida se estiver presente na requisição
            //Salva o produto atualizado no banco de dados
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ProdutoException("Produto não existe!"));

        if (produtoAtualizado.getDescricao() != null) {
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        }

        if (produtoAtualizado.getValor() != null) {
            produtoExistente.setValor(produtoAtualizado.getValor());
        }

        if (produtoAtualizado.getUnidadeMedida() != null) {
            produtoExistente.setUnidadeMedida(produtoAtualizado.getUnidadeMedida());
        }

        if (produtoAtualizado.getCategoria() != null && produtoAtualizado.getCategoria().getId() != null) {
            Categoria categoriaProduto = categoriaProdutoService.buscarCategoriaPorId(produtoAtualizado.getCategoria().getId());
            produtoExistente.setCategoria(categoriaProduto);
        }
        return produtoRepository.save(produtoExistente);
    }


            //Remove um produto
            //Se o produto não existir Retorne uma mensagem
            //Verificar se existem vendas associadas a este produto
   public void removerProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ProdutoException("Produto não existe!"));

        long quantidadeVendas = vendaRepository.countByProdutoId(id);
       if (quantidadeVendas > 0) {
           throw new TrataExecao.ProdutoException("Produto possui vendas cadastradas! Exclua a venda e tente novamente!");
       }
       // Remove o produto após remover as vendas associadas
       produtoRepository.deleteById(id);
   }
}

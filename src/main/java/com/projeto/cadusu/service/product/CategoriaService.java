package com.projeto.cadusu.service.product;

import com.projeto.cadusu.Repository.product.CategoriaRepository;
import com.projeto.cadusu.model.product.Categoria;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaProdutoRepository;

            //Lista todas as categorias
            //Está chama o método do repositório para obter todas as categorias
    public List<Categoria> listarCategorias() {
        return categoriaProdutoRepository.findAll();
    }

            //Adiciona uma nova categoria
            //Está chamdo o método do repositório e salva a nova categoria
    public Categoria adicionarCategoria(Categoria novaCategoria) {
        return categoriaProdutoRepository.save(novaCategoria);
    }

            //Busca uma categoria por id
            //Está chama o método do repositório para buscar a categoria pelo ID
            //Se não encontrada, lança a exceção CategoriaNotFoundException de TratarExecao
    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaProdutoRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ProdutoException ("Classificacao não encontrada"));
    }

            //Remove uma categoria pelo id
            //Chama o método do repositório para remover a categoria pelo ID
            //Se não encontrada, lança a exceção CategoriaNotFoundException de TratarExecao
            //Remove a categoria do repositório
    public void removerCategoriaPorId(Long id) {
        Categoria categoria = categoriaProdutoRepository.findById(id)
                .orElseThrow(() -> new TrataExecao.ProdutoException ("Categoria não encontrada"));

        categoriaProdutoRepository.delete(categoria);
    }
}

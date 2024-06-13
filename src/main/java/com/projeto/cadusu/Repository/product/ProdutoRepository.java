package com.projeto.cadusu.Repository.product;


import com.projeto.cadusu.model.Cliente;
import com.projeto.cadusu.model.product.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}

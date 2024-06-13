package com.projeto.cadusu.Repository.sale;


import com.projeto.cadusu.model.sale.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    long countByProdutoId(Long id);

    long countByClienteId(Long id);

}

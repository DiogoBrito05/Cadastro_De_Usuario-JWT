package com.projeto.cadusu.controller.sale;

import com.projeto.cadusu.model.sale.Venda;
import com.projeto.cadusu.service.exceptions.TrataExecao;
import com.projeto.cadusu.service.sale.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

            //Adiciona uma nova venda
            //Retorna a venda adicionada
            //Se tiver erro, retornar uma resposta de erro
    @PostMapping("/adicionarvenda")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adicionarVenda(@RequestBody Venda novaVenda, @RequestParam(required = false) Double desconto) {
        try {
            Venda vendaAdicionada = vendaService.adicionarVenda(novaVenda, desconto);
            return new ResponseEntity<>(vendaAdicionada, HttpStatus.CREATED);
        } catch (TrataExecao.VendaException | TrataExecao.ClienteException | TrataExecao.ProdutoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
}

             //Lista todas as vendas
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarVendas();
             //Aqui responde com a lista de vendas
        return new ResponseEntity<>(vendas, HttpStatus.OK);
    }

              //Cancela uma venda pelo seu id
              // Responde com a mensagem de sucesso
              //Se tiver algum erro, retornar uma resposta de erro
    @DeleteMapping("/cancelarvenda/{vendaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cancelarVenda(@PathVariable Long vendaId) {
        try {
            vendaService.cancelarVenda(vendaId);
            return new ResponseEntity<>("Venda cancelada com sucesso!", HttpStatus.OK);
        } catch (TrataExecao.VendaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


            //Faz consulta de uma venda pelo seu id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_DEFAULT')")
    public ResponseEntity<?> consultarVenda(@PathVariable Long id) {
        try {
            Venda venda= vendaService.consultarVenda(id);
            return new ResponseEntity<>(venda, HttpStatus.OK);
        } catch (TrataExecao.VendaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
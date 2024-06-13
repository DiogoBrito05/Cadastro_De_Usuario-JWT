package com.projeto.cadusu.service.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Stack;

@ControllerAdvice
public class TrataExecao {

            //Todo:Essa classe lida com o tratamendo de exeções dos endpoints REST
    //Lida com situações relacionadas a usuários
    public static class UsuarioException extends RuntimeException {
        public UsuarioException(String message) {
            super(message);
        }
    }

    //Lida com situações relacionadas a tarefas
    public static class TarefaException extends RuntimeException {
        public TarefaException(String message) {
            super(message);
        }
    }

    // Exceção para quando uma classificação não é encontrada
    public static class ClassificacaoNotFoundException extends RuntimeException {
        public ClassificacaoNotFoundException(String message) {
            super(message);
        }
    }

    // Lida com situações relacionadas a clientes
    public static class ClienteException extends RuntimeException {
        public ClienteException(String message) {
            super(message);
        }
    }

    //Lida com situações relacionadas a produtos
    public static class ProdutoException extends RuntimeException {
        public ProdutoException(String message) {
            super(message);
        }
    }

    //Lida com situações relacionadas a venda
    public static class VendaException extends RuntimeException {
        public VendaException(String message) {
            super(message);
        }
    }

}

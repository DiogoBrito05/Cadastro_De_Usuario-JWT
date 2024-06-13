CREATE TABLE tarefa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255),
    usuario_id BIGINT,
    concluido BOOLEAN,
    classificacao_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (classificacao_id) REFERENCES classificacao(id)
);
CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100),
    valor DOUBLE NOT NULL,
    unidade_medida VARCHAR(50),
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


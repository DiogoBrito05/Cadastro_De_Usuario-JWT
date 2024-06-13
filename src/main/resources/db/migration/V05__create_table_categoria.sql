CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome) values ('Tecnologia');
INSERT INTO categoria (nome) values ('Roupas');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Higiene Pessoal');
INSERT INTO categoria (nome) values ('Produto Limpeza');
INSERT INTO categoria (nome) values ('Drogaria');
INSERT INTO categoria (nome) values ('Cosméticos');
INSERT INTO categoria (nome) values ('Mecânica');
INSERT INTO categoria (nome) values ('Gás');
INSERT INTO categoria (nome) values ('Outros');

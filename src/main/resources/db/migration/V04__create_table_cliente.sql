CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	cpf varchar (14),
	logradouro VARCHAR(30),
	numero VARCHAR(30),
	complemento VARCHAR(100),
	bairro VARCHAR(30),
	cep VARCHAR(10),
	cidade VARCHAR(100),
	estado VARCHAR(100),
	ativo BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    nome VARCHAR(255),
    senha VARCHAR(255)
);

INSERT INTO usuario (email, nome, senha) VALUES ("carlos@sitiorecantodoqueijo.com", "Carlos", "$2a$12$K3sACJuHJRZRBdgRmWRDduysBH2/idJadVmy.U3XZqJsSREVNRELy");
INSERT INTO usuario (email, nome, senha) VALUES ("diogo@sitiorecantodoqueijo.com", "Diogo", "$2a$12$K3sACJuHJRZRBdgRmWRDduysBH2/idJadVmy.U3XZqJsSREVNRELy");

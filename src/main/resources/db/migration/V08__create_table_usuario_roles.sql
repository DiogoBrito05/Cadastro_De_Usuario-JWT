create table usuario_roles (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      usuario_id BIGINT,
      role VARCHAR(255),
      FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

INSERT INTO usuario_roles (usuario_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO usuario_roles (usuario_id, role) VALUES (2, 'ROLE_ADMIN');
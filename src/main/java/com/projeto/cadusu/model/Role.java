package com.projeto.cadusu.model;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ROLE_DEFAULT,
    ROLE_USER,
    ROLE_ADMIN;

            //Mapeia valores numéricos para tipos de roles
    private static final Map<Long, Role> ROLE_MAP = new HashMap<>();

            //Bloco estático para inicialização do mapa
            //Mapei o ordinal (índice) do role ára o próprio Role
    static {
        for (Role role : Role.values()) {
            ROLE_MAP.put((long) role.ordinal(), role);
        }
    }


            // Método estático para obter o Role a partir de um valor numérico
    public static Role fromValue(Long value) {
        if (!ROLE_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Valor de Role inválido: " + value);
        }
        return ROLE_MAP.get(value);
    }
}

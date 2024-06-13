package com.projeto.cadusu.security.cors.propertys;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("cadusu")
@Component
public class SrqApiProperty {


    // Essa propriedade define a origem permitida para requisições CORS
    private String originPermitida = "http://localhost:4200";

    // Instância da classe interna que encapsula configurações de segurança
    private final Seguranca seguranca = new Seguranca();

    // Método para obter a instância de Seguranca
    public Seguranca getSeguranca() {
        return seguranca;
    }

    public String getOriginPermitida() {
        return originPermitida;
    }

    public void setOriginPermitida(String originPermitida) {
        this.originPermitida = originPermitida;
    }

    // Classe estática interna que encapsula configurações de segurança
    public static class Seguranca {

        //Https está habilitado
        private boolean enableHttps;

        //verifica se o HTTPS está habilitado
        public boolean isEnableHttps() {
            return enableHttps;
        }

        // Método que configurar dinamicamente se o HTTPS está habilitado
        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }

}
package com.projeto.cadusu.security.cors;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projeto.cadusu.security.cors.propertys.SrqApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Autowired
    private SrqApiProperty srqApiProperty;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        // Está convertendo a requisição de resposta para HttpServlet
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // Configuração dos cabeçalhos CORS
        response.setHeader("Access-Control-Allow-Origin", srqApiProperty.getOriginPermitida());
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // Manipula as requisições de preflight (método OPTIONS)
        if ("OPTIONS".equals(request.getMethod()) && srqApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            response.setHeader("Access-Control-Max-Age", "3600");

            //Define o HTTP ok
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Continua a cadeia de filtros para requisições regulares
            chain.doFilter(req, resp);
        }

    }

}
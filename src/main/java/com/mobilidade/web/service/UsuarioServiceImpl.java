package com.mobilidade.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobilidade.web.model.UsuarioDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
    
private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    
    private WebClient webClient;

    @Override
    public String alterarUsuario(UsuarioDTO usuario) throws JsonProcessingException {
        LOGGER.info("PUT   REQUEST  - URL: {} - HEADERS: {} - REQUEST BODY: {}", "/usuario/alterar", usuario);
        ResponseEntity<String> response =   webClient.put()
                                                     .uri("/usuario/alterar")
                                                     .bodyValue(usuario)
                                                     .retrieve()
                                                     .toEntity(String.class)
                                                     .block();
        
        LOGGER.info("PUT   RESPONSE - URL: {} - RESPONSE BODY: {}", "/usuario/alterar", response);
        return response.getBody();
    }
}

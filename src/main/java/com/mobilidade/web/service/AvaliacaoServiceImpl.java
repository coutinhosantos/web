package com.mobilidade.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobilidade.web.exception.ApplicationException;
import com.mobilidade.web.model.Avaliacao;
import com.mobilidade.web.model.AvaliacaoDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AvaliacaoServiceImpl implements AvaliacaoService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoServiceImpl.class);
    
    private final WebClient webClient;
    
    @Override
    public Avaliacao cadastrar(AvaliacaoDTO avaliacao) throws JsonProcessingException {
        LOGGER.info("POST   REQUEST  - URL: {} - HEADERS: {} - REQUEST BODY: {}", "/avaliacao/avaliar", avaliacao);
        ResponseEntity<Avaliacao> response = webClient.post()
                                                      .uri("/avaliacao/avaliar")
                                                      .bodyValue(avaliacao)
                                                      .retrieve()
                                                      .toEntity(Avaliacao.class)
                                                      .block();
        LOGGER.info("POST   RESPONSE - URL: {} - RESPONSE BODY: {}", "/avaliacao/avaliar", response);
        return response.getBody();
    }

    @Override
    public void excluirAvaliacao(String id) throws JsonProcessingException {
        try {
            LOGGER.info("DELETE REQUEST  - URL: {}", "/avaliacao/avaliacao/".concat(id));
            webClient.delete().uri("/avaliacao/avaliacao/".concat(id)).retrieve().bodyToMono(Void.class).block();
            LOGGER.info("DELETE RESPONSE - URL: {}", "/avaliacao/avaliacao/".concat(id));
        } catch (final Exception exception) {
            final String exceptionResponse = String.format("Erro ao fazer a requisição DELETE para a url %s. ERRO: %s", "/avaliacao/avaliacao/", exception.getMessage());
            LOGGER.error(exceptionResponse, exception);
            throw new ApplicationException(500, exceptionResponse);
        }
    }

    @Override
    public Avaliacao visualizar(String id) throws JsonProcessingException {
        LOGGER.info("GET    REQUEST  - URL: {} ", "/avaliacao/avaliacao/" + id);
        Avaliacao avaliacao =  webClient.get()
                                        .uri("/avaliacao/avaliacao/" + id)
                                        .retrieve()
                                        .bodyToMono(Avaliacao.class)
                                        .block();
        LOGGER.info("GET    RESPONSE - URL: {} - RESPONSE BODY: {}", "/avaliacao/avaliacao/" + id, avaliacao);
        return avaliacao;
    }

    @Override
    public Avaliacao alterar(AvaliacaoDTO avaliacao) throws JsonProcessingException {
        LOGGER.info("PUT    REQUEST  - URL: {} - HEADERS: {} - REQUEST BODY: {}", "/avaliacao/alterarAvaliacao", avaliacao);
        ResponseEntity<Avaliacao> response =  webClient.put()
                                                       .uri("/avaliacao/alterarAvaliacao")
                                                       .bodyValue(avaliacao)
                                                       .retrieve()
                                                       .toEntity(Avaliacao.class)
                                                       .block();
        
        LOGGER.info("PUT    RESPONSE - URL: {} - RESPONSE BODY: {}", "/avaliacao/alterarAvaliacao", response.getBody());
        return response.getBody();
    }
}

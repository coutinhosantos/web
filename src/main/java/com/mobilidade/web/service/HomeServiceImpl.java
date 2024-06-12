package com.mobilidade.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobilidade.web.model.Avaliacao;
import com.mobilidade.web.model.Ranking;
import com.mobilidade.web.model.Usuario;
import com.mobilidade.web.model.UsuarioDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeServiceImpl.class);
    
    private WebClient webClient;
    
    @Override
    public Usuario login(UsuarioDTO usuario) throws JsonProcessingException {
        LOGGER.info("POST   REQUEST  - URL: {} - HEADERS: {} - REQUEST BODY: {}", "/usuario/cadastrar", usuario);
        ResponseEntity<Usuario> response =  webClient.post()
                                                     .uri("/usuario/login")
                                                     .bodyValue(usuario)
                                                     .retrieve()
                                                     .toEntity(Usuario.class)
                                                     .block();
        
        LOGGER.info("POST   RESPONSE - URL: {} - RESPONSE BODY: {}", "/usuario/cadastrar", response);
        return response.getBody();
    }

    @Override
    public String cadastrar(UsuarioDTO usuario) throws JsonProcessingException {
        LOGGER.info("POST   REQUEST  - URL: {} - HEADERS: {} - REQUEST BODY: {}", "/usuario/cadastrar", usuario);
        ResponseEntity<String> response =  webClient.post()
                                                    .uri("/usuario/cadastrar")
                                                    .bodyValue(usuario)
                                                    .retrieve()
                                                    .toEntity(String.class)
                                                    .block();
        
        LOGGER.info("POST   RESPONSE - URL: {} - RESPONSE BODY: {}", "/usuario/cadastrar", response);
        return response.getBody();
    }
    
    @Override
    public List<Avaliacao> listarAvaliacao() throws JsonProcessingException {
        LOGGER.info("GET    REQUEST  - URL: {} ", "/avaliacao/avaliacao");
        List<Avaliacao> list = webClient.get()
                                        .uri("/avaliacao/avaliacao")
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<Avaliacao>>() {})
                                        .block();
        LOGGER.info("GET    RESPONSE - URL: {} - RESPONSE BODY: {}", "/avaliacao/avaliacao", list);
        return list;
    }

    @Override
    public List<Avaliacao> listarAvaliacaoPorUsuario(String login) throws JsonProcessingException {
        LOGGER.info("GET    REQUEST  - URL: {} ", "/avaliacao/avaliacaoPorUsuario/".concat(login));
        List<Avaliacao> list = webClient.get()
                                        .uri("/avaliacao/avaliacaoPorUsuario/".concat(login))
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<Avaliacao>>() {})
                                        .block();
        LOGGER.info("GET    RESPONSE - URL: {} - RESPONSE BODY: {}", "/avaliacao/avaliacaoPorUsuario/".concat(login), list);
        return list;
    }

    @Override
    public List<Ranking> melhoresEmpresas() throws JsonProcessingException {
        LOGGER.info("GET    REQUEST  - URL: {} ", "/ranking/melhoresEmpresas");
        List<Ranking> list = webClient.get()
                                      .uri("/ranking/melhoresEmpresas")
                                      .retrieve()
                                      .bodyToMono(new ParameterizedTypeReference<List<Ranking>>() {})
                                      .block();
        LOGGER.info("GET    RESPONSE - URL: {} - RESPONSE BODY: {}", "/ranking/melhoresEmpresas", list);
        return list;
    }

    @Override
    public List<Ranking> pioresEmpresas() throws JsonProcessingException {
        LOGGER.info("GET    REQUEST  - URL: {} ", "/ranking/pioresEmpresas");
        List<Ranking> list = webClient.get()
                                      .uri("/ranking/pioresEmpresas")
                                      .retrieve()
                                      .bodyToMono(new ParameterizedTypeReference<List<Ranking>>() {})
                                      .block();
        LOGGER.info("GET    RESPONSE - URL: {} - RESPONSE BODY: {}", "/ranking/pioresEmpresas", list);
        return list;
    }
}

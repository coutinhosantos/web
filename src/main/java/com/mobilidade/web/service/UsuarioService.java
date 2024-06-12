package com.mobilidade.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobilidade.web.model.UsuarioDTO;

public interface UsuarioService {
    public String alterarUsuario(UsuarioDTO usuario) throws JsonProcessingException;
}

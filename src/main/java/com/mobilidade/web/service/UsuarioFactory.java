package com.mobilidade.web.service;

import com.mobilidade.web.model.Usuario;
import com.mobilidade.web.model.UsuarioDTO;

public interface UsuarioFactory {
    public Usuario getUsuario(UsuarioDTO usuario);
    public UsuarioDTO getUsuarioDTO(Usuario usuario);
}

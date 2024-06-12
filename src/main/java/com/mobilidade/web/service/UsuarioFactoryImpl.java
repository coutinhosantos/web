package com.mobilidade.web.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.mobilidade.web.model.Usuario;
import com.mobilidade.web.model.UsuarioDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UsuarioFactoryImpl implements UsuarioFactory {

    @Override
    public Usuario getUsuario(UsuarioDTO usuario) {
        return Usuario.builder()
                      .id(usuario.getId())
                      .nome(usuario.getNome())
                      .login(usuario.getLogin())
                      .senha(usuario.getSenha())
                      .dataCadastro(LocalDateTime.now())
                      .build();
    }

    @Override
    public UsuarioDTO getUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                         .id(usuario.getId())
                         .nome(usuario.getNome())
                         .login(usuario.getLogin())
                         .senha(usuario.getSenha())
                         .dataCadastro(usuario.getDataCadastro())
                         .build();
    }
}

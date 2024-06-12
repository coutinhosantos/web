package com.mobilidade.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobilidade.web.model.Usuario;
import com.mobilidade.web.model.UsuarioDTO;
import com.mobilidade.web.service.HomeService;
import com.mobilidade.web.service.StorageService;
import com.mobilidade.web.service.UsuarioFactory;
import com.mobilidade.web.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UsuarioController {
    
    @Autowired
    private final StorageService storageService;
    
    @Autowired
    private final UsuarioService service;
    
    @Autowired
    private final HomeService homeService;
    
    @Autowired
    private UsuarioFactory usuarioFactory;
    
    @Autowired
    HttpSession session;

    @PostMapping("/alterarUsuario")
    public ModelAndView alterarUsuario(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("Usuario") UsuarioDTO usuario,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam("senhaAtual") String senhaAtual, 
                                       @RequestParam("novaSenha") String novaSenha, 
                                       @RequestParam("confirmaNovaSenha") String confirmaNovaSenha) throws JsonProcessingException {
        ModelAndView view = null;
        view = new ModelAndView("/pages/login");
        if(null!=senhaAtual && null!=novaSenha && null!=confirmaNovaSenha && !"".equalsIgnoreCase(senhaAtual) && !"".equalsIgnoreCase(novaSenha) && !"".equalsIgnoreCase(confirmaNovaSenha)) {
            usuario.setSenha(senhaAtual);
            Usuario usuarioAtual = homeService.login(usuario);
            if(null!=usuarioAtual && novaSenha.equalsIgnoreCase(confirmaNovaSenha)) {
                usuarioAtual.setSenha(novaSenha);
                String usuarioAlterado = service.alterarUsuario(usuarioFactory.getUsuarioDTO(usuarioAtual));
                
                if("Usuario Alterado com sucesso".equalsIgnoreCase(usuarioAlterado)) {
                    if(null!=file) {
                        storageService.store(file, usuario.getId());
                    }
                    view.addObject("messageCadastro", "Usuario ".concat(usuario.getNome()).concat(" Alterado com sucesso"));
                    view.addObject("cadastrarUsuario", false);
                }else {
                    view.addObject("message", "Erro ao cadastrar Usuario");
                    view.addObject("cadastrarUsuario", true);
                }
            }
        }else {
            if(null!=file) {
                storageService.store(file, usuario.getId());
                view.addObject("messageCadastro", "Usuario ".concat(usuario.getNome()).concat(" Alterado com sucesso"));
                view.addObject("cadastrarUsuario", false);
            }
        }
        session.setAttribute("usuario", null);
        view.addObject("page", "Login");
        return view;
    }
}

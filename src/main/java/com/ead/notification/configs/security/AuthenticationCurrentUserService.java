package com.ead.notification.configs.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
//Classe para extrair informações do usuário corrente na api
public class AuthenticationCurrentUserService {

    public UserDetailsImpl getCuurent(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
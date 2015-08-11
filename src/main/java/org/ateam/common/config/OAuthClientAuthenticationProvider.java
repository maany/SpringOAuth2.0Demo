package org.ateam.common.config;

import org.ateam.common.model.Client;
import org.ateam.common.service.ClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created by OPSKMC on 8/11/15.
 */
@Component
public class OAuthClientAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    ClientDetailsServiceImpl clientDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("*** Client Identifier from form :" + username);
        // password verification
        Client client = (Client)clientDetailsService.loadClientByClientId(username);
        if(client.getClientSecret().equals(password)){
        //if(password validated)
            return new UsernamePasswordAuthenticationToken(username,password,client.getAuthorities());
        }else {
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

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
        System.out.println("***Comparing Client Secret : " + client.getClientSecret() + " and " + password);
        if(client.getClientSecret().equals(password)){
        //if(password validated)
            System.out.println("***Normal password case");
            return new UsernamePasswordAuthenticationToken(username,password,client.getAuthorities());

        }else if(client.getAdditionalInformation().get("REQUEST_SOURCE").equals(ClientDetailsServiceImpl.REQUEST_SOURCE)){
            System.out.println("***Null password case");
            client.getAdditionalInformation().remove("REQUEST_SOURCE");
            return new UsernamePasswordAuthenticationToken(username,password,client.getAuthorities());
        }else {
            System.out.println("***Invalid case");
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
        //return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

package org.ateam.common.config;

import org.ateam.common.model.User;
import org.ateam.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OPSKMC on 8/11/15.
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userDetailsService.getUser(username);
        if(user.getPassword().equals(password)){
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new UsernamePasswordAuthenticationToken(username,password,grantedAuths);
        }else{
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authorities) {
        return authorities.equals(UsernamePasswordAuthenticationToken.class);
    }
}

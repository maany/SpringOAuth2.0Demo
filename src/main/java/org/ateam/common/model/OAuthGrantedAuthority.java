package org.ateam.common.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

/**
 * Created by OPSKMC on 8/16/15.
 */
public class OAuthGrantedAuthority implements GrantedAuthority {
    private String role;

    public OAuthGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.length()==0?"ROLE_USER":role;
    }
}

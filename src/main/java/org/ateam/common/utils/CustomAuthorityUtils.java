package org.ateam.common.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * This class mimics methods from {@link org.springframework.security.core.authority.AuthorityUtils}
 * This class is required as we are using {@link org.springframework.security.core.authority.SimpleGrantedAuthority} instead of {@link org.springframework.security.core.GrantedAuthority}
 * and casting of collections of these two types creates issues.
 * //TODO write tests
 * Created by OPSKMC on 8/16/15.
 */
public class CustomAuthorityUtils {

    public static List<SimpleGrantedAuthority> createAuthorityList(String... roles) {
        List<SimpleGrantedAuthority> authorities;
        authorities = new ArrayList<SimpleGrantedAuthority>(roles.length);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    /**
     * Creates a array of GrantedAuthority objects from a comma-separated string
     * representation (e.g. "ROLE_A, ROLE_B, ROLE_C").
     *
     * @param authorityString the comma-separated string
     * @return the authorities created by tokenizing the string
     */
    public static List<SimpleGrantedAuthority> commaSeparatedStringToAuthorityList(String authorityString) {
        return createAuthorityList(StringUtils.tokenizeToStringArray(authorityString, ","));
    }

    /**
     *
     * @param simpleGrantedAuthorities the list of SimpleGrantedAuthority objects
     * @return the list of GrantedAuthority objects that can be used by Spring Security
     */
    public static List<GrantedAuthority> simpleGrantedAuthorityListToGrantedAuthorityList(Collection<SimpleGrantedAuthority> simpleGrantedAuthorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(simpleGrantedAuthorities.size());
        for(SimpleGrantedAuthority authority:simpleGrantedAuthorities){
            grantedAuthorities.add(authority);
        }
        return grantedAuthorities;
    }
}

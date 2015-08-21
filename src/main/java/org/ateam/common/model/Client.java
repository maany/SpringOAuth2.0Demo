package org.ateam.common.model;

import org.ateam.common.utils.ClientSpringOAuthUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created by OPSKMC on 8/9/15.
 */
@Entity(name = "client")
@Table(name = "oauth2_client")

public class Client  extends BaseOpenMRSData implements ClientDetails{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id; //rename field to something like key

    @ManyToOne
    @JoinColumn(name = "user")
    private User clientDeveloper;

    @Column(name = "client_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "redirection_uri")
    private String redirectionURI;

    @Column(name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(name = "client_identifier")
    private String clientIdentifier;
    @Column(name = "client_secret")
    private String clientSecret;

    private String website;

    // ================================
    // Fields from ClientDetailsService
    // ================================
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private Collection<Resource> resources;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private Collection<Scope> scopes;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private Collection<CustomGrantedAuthority> authorities;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private Collection<RedirectURI> registeredRedirectUris;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private Collection<CustomGrantType> customGrantTypes;

    @Transient
    private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

    private Integer refreshTokenValiditySeconds;

    private Integer accessTokenValiditySeconds;

    // ============
    // Constructors
    // ============
    protected Client(){

    }
    public Client(String clientId, String clientSecret,String resourceIds, String scopes, String grantTypes, String authorities,
                  String redirectUris) {
        this.clientIdentifier = clientId;
        this.clientSecret = clientSecret;

        if (StringUtils.hasText(resourceIds)) {
                this.resources = ClientSpringOAuthUtils.commaDelimitedStringToCollection(resourceIds,this,Resource.class);
        }

        if (StringUtils.hasText(scopes)) {
                this.scopes = ClientSpringOAuthUtils.commaDelimitedStringToCollection(scopes,this,Scope.class);
        }

        if (StringUtils.hasText(grantTypes)) {
            this.customGrantTypes = ClientSpringOAuthUtils.commaDelimitedStringToCollection(grantTypes,this,CustomGrantType.class);
        }
        else {
            CustomGrantType authorizationCode = new CustomGrantType("authorization_code");
            CustomGrantType refreshToken = new CustomGrantType("refresh_token");
            this.customGrantTypes.add(authorizationCode);
            this.customGrantTypes.add(refreshToken);
        }

        if (StringUtils.hasText(authorities)) {
            this.authorities = ClientSpringOAuthUtils.commaDelimitedStringToCollection(authorities,this,CustomGrantedAuthority.class);
        }

        if (StringUtils.hasText(redirectUris)) {
            this.registeredRedirectUris = ClientSpringOAuthUtils.commaDelimitedStringToCollection(redirectUris,this,RedirectURI.class);
        }
    }




    public User getClientDeveloper() {
        return clientDeveloper;
    }

    public void setClientDeveloper(User clientDeveloper) {
        this.clientDeveloper = clientDeveloper;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
    }

    public String getRedirectionURI() {
        return redirectionURI;
    }

    public void setRedirectionURI(String redirectionURI) {
        this.redirectionURI = redirectionURI;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    //==========================================
    // Getters/ Setters for ClientDetailsService
    //==========================================

    public Collection<Resource> getResources() {
        return resources;
    }

    public void setResources(Collection<Resource> resources) {
        this.resources = resources;
    }

    public Collection<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(Collection<Scope> scopes) {
        this.scopes = scopes;
    }

    public void setAuthorities(Collection<CustomGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Collection<RedirectURI> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Collection<RedirectURI> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public void setCustomGrantTypes(Collection<CustomGrantType> customGrantTypes) {
        this.customGrantTypes = customGrantTypes;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public void setClientId(String clientId) {
        setClientIdentifier(clientId);
    }
    @Override
    public String getClientId() {
        return getClientIdentifier();
    }
    public String getClientSecret(){
        return clientSecret;
    }
    @Override
    public Set<String> getResourceIds() {
        return ClientSpringOAuthUtils.parseResources(resources);
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return this.scopes != null && !this.scopes.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return ClientSpringOAuthUtils.parseScope(scopes);
    }

    @Override
    public Set<String> getCustomGrantTypes() {
        return ClientSpringOAuthUtils.parseAuthorizedGrantTypes(customGrantTypes);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return ClientSpringOAuthUtils.parseRedirectURIs(registeredRedirectUris);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return ClientSpringOAuthUtils.parseAuthorities(authorities);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }


    @Override
    @Column(name="retired")
    @Access(AccessType.PROPERTY)
    public boolean isRetired(){
        return retired;
    }

    @Override
    @Access(AccessType.PROPERTY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
    public User getCreator(){
        return creator;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public enum ClientType {
        WEB_APPLICATION, USER_AGENT_BASED_APPLICATION, NATIVE_APPLICATION;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accessTokenValiditySeconds==null) ? 0 : accessTokenValiditySeconds);
        result = prime * result + ((refreshTokenValiditySeconds == null) ? 0 : refreshTokenValiditySeconds);
        result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime * result + ((customGrantTypes == null) ? 0 : customGrantTypes.hashCode());
        result = prime * result + ((clientIdentifier == null) ? 0 : clientIdentifier.hashCode());
        result = prime * result + ((clientSecret == null) ? 0 : clientSecret.hashCode());
        result = prime * result + ((registeredRedirectUris == null) ? 0 : registeredRedirectUris.hashCode());
        result = prime * result + ((resources == null) ? 0 : resources.hashCode());
        result = prime * result + ((scopes == null) ? 0 : scopes.hashCode());
        result = prime * result + additionalInformation.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (accessTokenValiditySeconds != other.accessTokenValiditySeconds)
            return false;
        if (refreshTokenValiditySeconds != other.refreshTokenValiditySeconds)
            return false;
        if (authorities == null) {
            if (other.authorities != null)
                return false;
        }
        else if (!authorities.equals(other.authorities))
            return false;
        if (customGrantTypes == null) {
            if (other.customGrantTypes != null)
                return false;
        }
        else if (!customGrantTypes.equals(other.customGrantTypes))
            return false;
        if (clientIdentifier == null) {
            if (other.clientIdentifier != null)
                return false;
        }
        else if (!clientIdentifier.equals(other.clientIdentifier))
            return false;
        if (clientSecret == null) {
            if (other.clientSecret != null)
                return false;
        }
        else if (!clientSecret.equals(other.clientSecret))
            return false;
        if (registeredRedirectUris == null) {
            if (other.registeredRedirectUris != null)
                return false;
        }
        else if (!registeredRedirectUris.equals(other.registeredRedirectUris))
            return false;
        if (resources == null) {
            if (other.resources != null)
                return false;
        }
        else if (!resources.equals(other.resources))
            return false;
        if (scopes == null) {
            if (other.scopes != null)
                return false;
        }
        else if (!scopes.equals(other.scopes))
            return false;
        if (additionalInformation == null) {
            if (other.additionalInformation != null)
                return false;
        }
        else if (!additionalInformation.equals(other.additionalInformation))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String auths="";
        for(GrantedAuthority auth:authorities)
            auths +=auth.toString() + " ";
        return "Client [clientId=" + clientIdentifier + ", clientSecret=" + clientSecret + ", scope=" + scopes
                + ", resourceIds=" + resources + ", customGrantTypes=" + customGrantTypes
                + ", registeredRedirectUris=" + registeredRedirectUris + ", authorities=" + authorities
                + ", accessTokenValiditySeconds=" + accessTokenValiditySeconds + ", refreshTokenValiditySeconds="
                + refreshTokenValiditySeconds + ", additionalInformation=" + additionalInformation + "]"
                + "\n\n"
                + "scopes : " + scopes.size() + "\n"
                + "granted auth : " + auths;
    }
}

package org.ateam.common.model;

import org.ateam.common.utils.CustomAuthorityUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_resource_ids", joinColumns = @JoinColumn(name="client_id"))
    @Column(name="resource_ids")
   // @JoinTable(name = "oauth2_client_resource_ids", inverseJoinColumns = @JoinColumn(name = "resource_id_key"),joinColumns = @JoinColumn(name="client_id"))
 //   @GenericGenerator(name="hilo-gen", strategy="hilo")
   // @CollectionId(columns = {@Column(name = "resource_id_primary_key")},generator = "hilo-gen",type = @Type(type = "long"))
    private Set<String> resourceIds = new HashSet<String>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_scopes", joinColumns = @JoinColumn(name="client_id"))
    @Column(name="scope")
//    @JoinTable(name = "oauth2_client_scopes", inverseJoinColumns = @JoinColumn(name = "scope_key"),joinColumns = @JoinColumn(name="client_id"))
    //@GenericGenerator(name="hilo-gen", strategy="hilo")
    //@CollectionId(columns = {@Column(name = "scope_primary_key")},generator = "hilo-gen",type = @Type(type = "long"))
    private Set<String> scope = new HashSet<String>();

    @ElementCollection(fetch = FetchType.EAGER) //todo make this ManyToOne
    @CollectionTable(name = "oauth2_client_granted_authorites", joinColumns = @JoinColumn(name="client_id"))
    @Column(name="granted_authorities")
//    @JoinTable(name = "oauth2_client_authorities", inverseJoinColumns = @JoinColumn(name = "authorities_key"),joinColumns = @JoinColumn(name="client_id"))
    //@GenericGenerator(name="hilo-gen", strategy="hilo")
    //@CollectionId(columns = {@Column(name = "authorities_primary_key")},generator = "hilo-gen",type = @Type(type = "long"))
    private Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_redirect_uri", joinColumns = @JoinColumn(name="client_id"))
    @Column(name="redirect_uri")
//    @JoinTable(name = "oauth2_client_redirect_uri", inverseJoinColumns = @JoinColumn(name = "redirect_uri_key"),joinColumns = @JoinColumn(name="client_id"))
    //@GenericGenerator(name="hilo-gen", strategy="hilo")
    //@CollectionId(columns = {@Column(name = "redirect_uri_primary_key")},generator = "hilo-gen",type = @Type(type = "long"))
    private Set<String> registeredRedirectUris = new HashSet<String>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_grant_types", joinColumns = @JoinColumn(name="client_id"))
    @Column(name="grant_types")
//    @JoinTable(name = "oauth2_client_grant_types", inverseJoinColumns = @JoinColumn(name = "grant_types_key"),joinColumns = @JoinColumn(name="client_id"))
    //@GenericGenerator(name="hilo-gen", strategy="hilo")
    //@CollectionId(columns = {@Column(name = "grant_types_primary_key")},generator = "hilo-gen",type = @Type(type = "long"))
    private Set<String> authorizedGrantTypes = new HashSet<String>();

    /**
     * All additional properties have been defined before-hand. This variable will be deprecated soon
     * It is transient because Hibernate cannot persist ElementCollection of type Object
     * It is being used as a placeholder only, to be returned by the method {@link org.springframework.security.oauth2.provider.ClientDetails#getAdditionalInformation()}
     */
    //@ElementCollection
    @Transient
    private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

    //@JsonProperty("refresh_token_validity")
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
            Set<String> resources = StringUtils.commaDelimitedListToSet(resourceIds);
            if (!resources.isEmpty()) {
                this.resourceIds = resources;
            }
        }

        if (StringUtils.hasText(scopes)) {
            Set<String> scopeList = StringUtils.commaDelimitedListToSet(scopes);
            if (!scopeList.isEmpty()) {
                this.scope = scopeList;
            }
        }

        if (StringUtils.hasText(grantTypes)) {
            this.authorizedGrantTypes = StringUtils.commaDelimitedListToSet(grantTypes);
        }
        else {
            this.authorizedGrantTypes = new HashSet<String>(Arrays.asList("authorization_code", "refresh_token"));
        }

        if (StringUtils.hasText(authorities)) {
            this.authorities = CustomAuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        }

        if (StringUtils.hasText(redirectUris)) {
            this.registeredRedirectUris = StringUtils.commaDelimitedListToSet(redirectUris);
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
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return CustomAuthorityUtils.simpleGrantedAuthorityListToGrantedAuthorityList(authorities);
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
        result = prime * result + ((authorizedGrantTypes == null) ? 0 : authorizedGrantTypes.hashCode());
        result = prime * result + ((clientIdentifier == null) ? 0 : clientIdentifier.hashCode());
        result = prime * result + ((clientSecret == null) ? 0 : clientSecret.hashCode());
        result = prime * result + ((registeredRedirectUris == null) ? 0 : registeredRedirectUris.hashCode());
        result = prime * result + ((resourceIds == null) ? 0 : resourceIds.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
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
        if (authorizedGrantTypes == null) {
            if (other.authorizedGrantTypes != null)
                return false;
        }
        else if (!authorizedGrantTypes.equals(other.authorizedGrantTypes))
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
        if (resourceIds == null) {
            if (other.resourceIds != null)
                return false;
        }
        else if (!resourceIds.equals(other.resourceIds))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        }
        else if (!scope.equals(other.scope))
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
        for(SimpleGrantedAuthority auth:authorities)
            auths +=auth.toString() + " ";
        return "Client [clientId=" + clientIdentifier + ", clientSecret=" + clientSecret + ", scope=" + scope
                + ", resourceIds=" + resourceIds + ", authorizedGrantTypes=" + authorizedGrantTypes
                + ", registeredRedirectUris=" + registeredRedirectUris + ", authorities=" + authorities
                + ", accessTokenValiditySeconds=" + accessTokenValiditySeconds + ", refreshTokenValiditySeconds="
                + refreshTokenValiditySeconds + ", additionalInformation=" + additionalInformation + "]"
                + "\n\n"
                + "scopes : " + scope.size() + "\n"
                + "granted auth : " + auths;
    }
}

package org.ateam.common.model;

import javax.persistence.*;

/**
 * Created by OPSKMC on 8/19/15.
 */
@Entity
@Table(name = "oauth2_client_redirect_uri")
public class RedirectURI implements Parameterized {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id" )
    private Client client;

    public RedirectURI() {
    }

    public RedirectURI(String redirectURI) {

        this.redirectURI = redirectURI;
    }

    @Basic
    private String redirectURI;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    @Override
    public String getParameter() {
        return redirectURI;
    }

    @Override
    public void setParameter(String parameter) {
        this.redirectURI=parameter;
    }
}

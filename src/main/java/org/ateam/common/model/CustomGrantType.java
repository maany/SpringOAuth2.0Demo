package org.ateam.common.model;

import javax.persistence.*;

/**
 * Created by OPSKMC on 8/19/15.
 */
@Entity
@Table(name ="oauth2_client_grant_types")
public class CustomGrantType implements Parameterized{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id" )
    private Client client;

    public CustomGrantType() {
    }

    public CustomGrantType(Client client, String grantType) {
        this.client = client;
        this.grantType = grantType;
    }

    @Basic
    private String grantType;

    public CustomGrantType(String grantType) {
        this.grantType = grantType;
    }

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

    public String getAuthorizedGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @Override
    public String getParameter() {
        return grantType;
    }

    @Override
    public void setParameter(String parameter) {
        this.grantType = parameter;
    }
}

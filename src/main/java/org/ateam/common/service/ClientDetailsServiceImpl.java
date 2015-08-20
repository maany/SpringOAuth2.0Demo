package org.ateam.common.service;

import org.ateam.common.dao.ClientDAO;
import org.ateam.common.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by OPSKMC on 8/9/15.
 */

public class ClientDetailsServiceImpl implements ClientDetailsService {
    public static final String REQUEST_SOURCE= ClientDetailsServiceImpl.class.getName();
    @Autowired
    ClientDAO dao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        System.out.println("***Fetching client details for " + clientId);
        Client client = dao.loadClientByClientId(clientId);
        client.getAdditionalInformation().put("REQUEST_SOURCE",REQUEST_SOURCE);
        System.out.println(client.toString());
        return client;
    }
    @Transactional
    public void saveOrUpdateClient(Client client){
        dao.saveOrUpdateClient(client);
    }
    @Transactional
    public void updateClient(Client client){
        dao.updateClient(client);
    }
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public Client getClientById(Integer id){
        Client client = (Client)dao.getClientById(id);
        System.out.println(client.toString());
        return client;
    }
    @Transactional
    public void deleteClient(Client client){
        dao.deleteClient(client);

    }
    @Transactional
    public void persistClient(Client client){
        dao.persistClient(client);
    }

}

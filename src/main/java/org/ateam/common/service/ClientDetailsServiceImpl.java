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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by OPSKMC on 8/9/15.
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    ClientDAO dao;
    @Override
    @Transactional
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = dao.loadClientByClientId(clientId);
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
    @Transactional
    public void getClientById(Integer id){
        dao.getClientById(id);
    }
    @Transactional
    public void deleteClient(Client client){
        dao.deleteClient(client);
    }

}

package org.ateam.common.dao;

import org.ateam.common.model.Client;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by OPSKMC on 8/11/15.
 */
@Repository
public class ClientDAO {
    @Autowired
    SessionFactory sessionFactory;

    public void saveOrUpdateClient(Client client){
        sessionFactory.getCurrentSession().saveOrUpdate(client);
    }
    public void updateClient(Client client){
        sessionFactory.getCurrentSession().update(client);
    }
    public Client getClientById(Integer id){
        return (Client)sessionFactory.getCurrentSession().load(Client.class,id);
    }
    public Client deleteClient(Client client){
        sessionFactory.getCurrentSession().delete(client);
        return client;
    }
    public Client loadClientByClientId(String clientId){
        List<Client> clients = sessionFactory.getCurrentSession().createQuery("from client where clientIdentifier = :clientIdentifier").setParameter("clientIdentifier",clientId).list();
        if(clients.isEmpty())
            return null;
        return clients.get(0);
    }
    public void persistClient(Client client){
        sessionFactory.getCurrentSession().persist(client);
    }
}

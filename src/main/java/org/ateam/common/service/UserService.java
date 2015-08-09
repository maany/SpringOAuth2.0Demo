package org.ateam.common.service;

import org.ateam.common.dao.OAuth2DAO;
import org.ateam.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by OPSKMC on 8/6/15.
 */
@Service
public class UserService {
    public UserService(){

    }
    @Autowired
    private OAuth2DAO dao;
    @Transactional
    public void addUser(User user){
        dao.saveOrUpdateUser(user);
    }
    @Transactional
    public User deleteUser(int id){
        User user = dao.deleteUser(id);
        return user;
    }
    @Transactional
    public void updateUser(User user){
        dao.updateUser(user);
    }
    @Transactional
    public User getUser(int id){
        User user = dao.getUser(id);
        return user;
    }
    @Transactional
    public User getUser(String name){
        User user = dao.getUserByUsername(name);
        return user;
    }
}


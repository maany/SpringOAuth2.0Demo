package org.ateam.common.dao;

import org.ateam.common.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by OPSKMC on 8/6/15.
 */
@Repository
public class OAuth2DAO {
    public OAuth2DAO(){

    }
    @Autowired
    private SessionFactory sessionFactory;
    public User getUser(int id){
        User user = (User)sessionFactory.getCurrentSession().load(User.class,new Integer(id));
        return user;
    }
    public User deleteUser(int id){
        User user = (User)sessionFactory.getCurrentSession().load(User.class, new Integer(id));
        if(user!=null){
            sessionFactory.getCurrentSession().delete(user);
            return user;
        }
        return null;
    }
    public void saveOrUpdateUser(User user){
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }
    public void updateUser(User user){
        sessionFactory.getCurrentSession().update(user);
    }
    public User getUserByUsername(String username){
        User user = null;
        Query query = sessionFactory.getCurrentSession().createQuery("from User where username= :name");
        query.setParameter("name",username);
        List<User> users = query.list();
        if(users.isEmpty())
            return null;
        return users.get(0);
    }
}

package org.ateam.common.model;

import javax.persistence.*;

/**
 * Created by OPSKMC on 8/5/15.
 */
@Entity
@Table
public class User  {
    public User(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @Basic
    private String username;
    @Basic
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

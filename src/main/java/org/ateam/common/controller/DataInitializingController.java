package org.ateam.common.controller;

import org.ateam.common.model.Client;
import org.ateam.common.model.User;
import org.ateam.common.service.ClientDetailsServiceImpl;
import org.ateam.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by OPSKMC on 8/7/15.
 */
@Controller
public class DataInitializingController {
    @Autowired
    UserService userService;
    @Autowired
    ClientDetailsServiceImpl clientDetailsService;
    @RequestMapping(value = "/initUsers")
    public
    @ResponseBody
    String initUsers(ModelMap map) {
        String response = "Failed to add Users";
        User user = new User();
        user.setUsername("Mayank");
        user.setPassword("password");
        User user1 = new User();
        user1.setUsername("Bob");
        user1.setPassword("bobp");

        try{
            userService.addUser(user);
            userService.addUser(user1);
            response = "2 users added successfully";
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }
    @RequestMapping(value= "/initClients")
    public
    @ResponseBody
    String initClients(ModelMap map){
        String response = "Failed to add clients";
        Client client = new Client("my-trusted-client-with-secret","somesecret","test","read,write","password,authorization_code,implicit,client_credentials,refresh_token","ROLE_CLIENT","http://anywhere?key=value");
        Client client2 = new Client("openmrs-client","secret","test","read,write","password,authorization_code,implicit,client_credentials,refresh_token","ROLE_CLIENT","http://anywhere?key=value");
        try{
            clientDetailsService.persistClient(client);
            clientDetailsService.saveOrUpdateClient(client2);
            response = "Clients added successfully";
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }
    @RequestMapping(value = "/client/{id}")
    public
    @ResponseBody
    String getClient(@PathVariable int id, ModelMap map){
        Client client  = clientDetailsService.getClientById(id);
        return client.toString();
    }


}

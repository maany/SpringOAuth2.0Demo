package org.ateam.common.controller;

import org.ateam.common.model.User;
import org.ateam.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by OPSKMC on 8/7/15.
 */
@Controller
public class DataInitializingController {
    @Autowired
    UserService userService;
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
}

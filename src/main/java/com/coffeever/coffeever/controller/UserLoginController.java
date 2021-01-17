package com.coffeever.coffeever.controller;

import com.coffeever.coffeever.model.User;
import com.coffeever.coffeever.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserLoginController {

    @Autowired
    UserCrudService userCrudService;


    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public User loginCheck(@RequestBody User user) {
        if(userCrudService.findUserById(user.getGoogle_id()) == null) {
            User newUser = userCrudService.addUser(user);
            return newUser;
        }
        else {
            User existUser = userCrudService.findUserById(user.getGoogle_id());
           return existUser;
        }


    }
}
    /*
    @PostMapping("/login")
    public User googleIDReturn(@RequestParam Long google_id,User user) {
        // try-catch kullanılacak fırlatılacak hataya bak
        if(userCrudService.findUserById(google_id) != null) {
            if(userCrudService.findUserById(google_id).equals(user.getGoogle_id())) {
                userCrudService.updateUser(user);
            }
            user = userCrudService.findUserById(google_id);
            return user;
        }
        if(userCrudService.findUserById(google_id) == null) {
            user = new User();
            user.setGoogle_id(google_id);
           return userCrudService.addUser(user);

        }

        else {
            return null;
        }


    }*/



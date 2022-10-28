package com.example.demo_log.service;

import com.example.demo_log.bo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public User register(User user) throws InterruptedException {
        user.setUsername(user.getUsername() + " Reg");
        user.setPassword(user.getPassword() + " Reg");
        return user;
    }

    public List<User> register(ArrayList<User> users) {
        return users.stream().peek(user -> {
            user.setUsername(user.getUsername() + " Reg");
            user.setPassword(user.getPassword() + " Reg");
        }).toList();
    }


}

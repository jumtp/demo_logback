package com.example.demo_log.controller;

import com.example.demo_log.bo.User;
import com.example.demo_log.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public List<User> userRegister(@RequestBody ArrayList<User> users)  {

        log.trace("trace logger");
        log.debug("debug logger");
        log.info("Info logger");
        log.warn("warn logger");
        log.error("error logger");


        return userService.register(users);
    }
}

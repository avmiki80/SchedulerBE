package ru.avid.scheduler.auth.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avid.scheduler.auth.entity.User;
import ru.avid.scheduler.auth.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Log
public class AuthController {
    private UserService userService;
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PutMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user){
        userService.save(user);
        return ResponseEntity.ok().build();
    }

}

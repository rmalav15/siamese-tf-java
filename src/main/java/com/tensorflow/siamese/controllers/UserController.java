package com.tensorflow.siamese.controllers;

import com.tensorflow.siamese.models.User;
import com.tensorflow.siamese.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get/all")
    ResponseEntity getAll() {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

}

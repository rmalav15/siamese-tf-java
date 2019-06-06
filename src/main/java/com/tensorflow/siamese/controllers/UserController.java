package com.tensorflow.siamese.controllers;

import com.tensorflow.siamese.models.User;
import com.tensorflow.siamese.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/delete/{id}")
    ResponseEntity delete(@PathVariable UUID id) {
        User user = userRepository.getOne(id);
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

}

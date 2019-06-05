package com.tensorflow.siamese.controllers;

import com.tensorflow.siamese.models.User;
import com.tensorflow.siamese.repositories.UserRepository;
import com.tensorflow.siamese.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/enroll")
public class EnrollmentController {

    @Value("${images.save.path:src/main/resources/Images}")
    private String path;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    ResponseEntity enrollNew(@RequestParam("name") String name,
                             @RequestParam("files") List<MultipartFile> images) throws IOException {

        List<Path> imagePaths = new ArrayList<>();
        for(MultipartFile file: images){
            imagePaths.add(write(file));
        }
        User user = enrollmentService.enrollNew(imagePaths, name);
        return ResponseEntity.ok(user);
    }

    private Path write(MultipartFile file) throws IOException {
        Path dir = Paths.get(path);
        Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());

        OutputStream os = Files.newOutputStream(filepath);
        os.write(file.getBytes());

        return filepath;
    }
}

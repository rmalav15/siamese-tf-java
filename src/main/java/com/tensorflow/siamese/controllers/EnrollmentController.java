package com.tensorflow.siamese.controllers;

import com.tensorflow.siamese.models.User;
import com.tensorflow.siamese.services.EnrollmentService;
import com.tensorflow.siamese.services.ImageProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/enroll")
@Slf4j
public class EnrollmentController {

    @Value("${images.save.path:src/main/resources/Images}")
    private String path;

    @Autowired
    private EnrollmentService enrollmentService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    ResponseEntity enrollNew(@RequestParam("name") String name,
                             @RequestParam("files") List<MultipartFile> images) {

        try {
            List<Path> imagePaths = new ArrayList<>();
            for (MultipartFile file : images) {
                imagePaths.add(ImageProcessingService.write(file, path));
            }
            User user = enrollmentService.enrollNew(imagePaths, name);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.debug("Exception in enrollNew Api", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to Enroll Due to some error.");
        }

    }


}

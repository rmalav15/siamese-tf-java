package com.tensorflow.siamese.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@RestController
@RequestMapping("/enroll")
@MultipartConfig(maxFileSize = 10737418240L, maxRequestSize = 10737418240L, fileSizeThreshold = 52428800)
public class EnrollmentController {

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    ResponseEntity enrollNew(@RequestParam("name") String name,
                             @RequestParam("files") List<MultipartFile> images) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

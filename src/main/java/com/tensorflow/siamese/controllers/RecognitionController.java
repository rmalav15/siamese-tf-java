package com.tensorflow.siamese.controllers;

import com.tensorflow.siamese.services.ImageProcessingService;
import com.tensorflow.siamese.services.RecognitionService;
import com.tensorflow.siamese.services.RecognitionResult;
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

@RestController
@RequestMapping("/")
@Slf4j
public class RecognitionController {

    @Value("${images.save.path:src/main/resources/Images}")
    private String path;

    @Autowired
    private RecognitionService recognitionService;

    @RequestMapping(value = "/recog", method = RequestMethod.POST)
    ResponseEntity recognize(@RequestParam("image") MultipartFile image, @RequestParam("minConfidence") Double minConfidence
            , @RequestParam("topTwoMinGap") Double topTwoMinGap) {
        try {
            Path imagePath = ImageProcessingService.write(image, path);
            RecognitionResult result = recognitionService.recognise(imagePath, minConfidence, topTwoMinGap);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.debug("Exception in recognize Api", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to Recognize Due to some error.");
        }

    }
}

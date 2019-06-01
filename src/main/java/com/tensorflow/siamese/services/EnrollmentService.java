package com.tensorflow.siamese.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ij.ImagePlus;
import javassist.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    void enrollNew(List<Path> images, String name) throws JsonProcessingException, FileNotFoundException;

    void updateEnrolled(List<Path> images, UUID uuid) throws Exception;
}

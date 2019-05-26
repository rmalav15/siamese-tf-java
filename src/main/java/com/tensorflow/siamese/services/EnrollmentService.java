package com.tensorflow.siamese.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ij.ImagePlus;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    void enrollNew(List<ImagePlus> images, String name) throws JsonProcessingException;

    void updateEnrolled(List<ImagePlus> images, UUID uuid) throws IOException;
}

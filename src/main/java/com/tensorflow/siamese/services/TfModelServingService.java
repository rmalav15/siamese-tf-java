package com.tensorflow.siamese.services;

import ij.ImagePlus;
import org.tensorflow.Session;

import java.nio.file.Path;
import java.util.List;

public interface TfModelServingService {
    Session startSession();

    void initializeGraph(Session sess, Path path);

    List<Double> forwad(Session sess, ImagePlus Image);

    void closeSession();
}


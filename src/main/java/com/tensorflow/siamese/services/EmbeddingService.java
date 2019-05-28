package com.tensorflow.siamese.services;

import ij.ImagePlus;

import java.nio.file.Path;
import java.util.List;

public interface EmbeddingService {

    void startService();

    List<Double> getEmbeddings(ImagePlus image);

    void closeService();
}

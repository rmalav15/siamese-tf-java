package com.tensorflow.siamese.services;

import ij.ImagePlus;

import java.util.List;

public interface EmbeddingService {

    List<Double> getEmbeddings(ImagePlus image);
}

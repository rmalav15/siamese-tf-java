package com.tensorflow.siamese.services;

import java.nio.file.Path;
import java.util.List;

public interface EmbeddingService {

    void startService();

    List<Double> getEmbeddings(Path imagePath);

    void closeService();
}

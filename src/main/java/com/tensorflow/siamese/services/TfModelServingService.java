package com.tensorflow.siamese.services;

import java.nio.file.Path;

public interface TfModelServingService {

    void initializeGraph(String path);

    float[] forward(Path imagePath);

    void closeGraph();
}


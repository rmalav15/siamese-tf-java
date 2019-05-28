package com.tensorflow.siamese.services;

import org.tensorflow.Tensor;

import java.util.List;

public interface TfModelServingService {

    void initializeGraph(String path);

    double[] forward(Tensor Image);

    void closeGraph();
}


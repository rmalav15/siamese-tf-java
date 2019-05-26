package com.tensorflow.siamese.services.impl;

import com.tensorflow.siamese.services.TfModelServingService;
import ij.ImagePlus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Session;

import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
public class WriterRecognitionModelSerivce implements TfModelServingService {

    private Graph graph;

    @Override
    public Session startSession() {
        return null;
    }

    @Override
    public void initializeGraph(Session session, Path checkpointPath) {

    }

    @Override
    public List<Double> forward(Session session, ImagePlus Image) {
        return null;
    }

    @Override
    public void closeSession(Session session) {

    }
}

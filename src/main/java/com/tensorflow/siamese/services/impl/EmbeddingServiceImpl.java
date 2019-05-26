package com.tensorflow.siamese.services.impl;

import com.tensorflow.siamese.services.EmbeddingService;
import com.tensorflow.siamese.services.TfModelServingService;
import ij.ImagePlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tensorflow.Session;

import java.nio.file.Path;
import java.util.List;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    @Autowired
    private TfModelServingService tfModelServingService;

    private Session session;

    @Override
    public void startService(Path path) {
        session = tfModelServingService.startSession();
        tfModelServingService.initializeGraph(session, path);
    }

    @Override
    public List<Double> getEmbeddings(ImagePlus image) {
        return tfModelServingService.forward(session, image);
    }

    @Override
    public void closeService() {
        tfModelServingService.closeSession(session);
    }
}

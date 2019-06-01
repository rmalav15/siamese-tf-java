package com.tensorflow.siamese.services.impl;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.tensorflow.siamese.services.TfModelServingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.nio.FloatBuffer;

@Service
@Slf4j
public class WriterRecognitionModelSerivce implements TfModelServingService {

    private Session session;

    @Value("${tensorflow.model.emb.size:128}")
    private int embSize;

    @Override
    public void initializeGraph(String path) {
        SavedModelBundle modelBundle = SavedModelBundle.load(path, "serve");
        session = modelBundle.session();
    }

    @Override
    public float[] forward(Tensor images) {
        Preconditions.checkNotNull(session, "Session cant be null");
        Tensor embTensor = session.runner()
                .fetch("embeddings")
                .feed("input_images", images)
                .run().get(0);
        FloatBuffer floatBuffer = FloatBuffer.allocate(embSize);
        embTensor.writeTo(floatBuffer);
        return floatBuffer.array();
    }


    @Override
    public void closeGraph() {
        Optional<Session> optSession = Optional.fromNullable(session);
        if (optSession.isPresent()) {
            session.close();
        }
    }

}

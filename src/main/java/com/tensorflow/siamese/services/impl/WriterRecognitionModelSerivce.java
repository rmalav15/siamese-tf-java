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
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Service
@Slf4j
public class WriterRecognitionModelSerivce implements TfModelServingService {

    private Session session;

    @Value("${tensorflow.model.emb.size:128}")
    private int embSize;

    @Override
    public void initializeGraph(String path) {
        Optional<Session> optSession = Optional.fromNullable(session);
        if (!optSession.isPresent()) {
            SavedModelBundle modelBundle = SavedModelBundle.load(path, "serve");
            session = modelBundle.session();
            log.info("Starting TF session with model from: " + path);
        }
    }

    //TODO: add batch process support
    @Override
    public float[] forward(Path imagePath) {
        Preconditions.checkNotNull(session, "Session cant be null");
        Tensor<String> imagePathTensor = null;
        imagePathTensor = Tensor.create(imagePath.toString().getBytes(StandardCharsets.UTF_8), String.class);
        Tensor embTensor = session.runner()
                .fetch("embeddings")
                .feed("image_path_tensors", imagePathTensor)
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

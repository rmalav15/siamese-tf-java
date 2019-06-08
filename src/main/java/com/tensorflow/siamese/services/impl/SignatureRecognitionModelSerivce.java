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
import org.tensorflow.Tensors;

import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Service
@Slf4j
public class SignatureRecognitionModelSerivce implements TfModelServingService {

    private Session session;

    @Value("${tensorflow.model-wo-pre.emb.size:128}")
    private int embSize;

    @Override
    public void initializeGraph(String path) {
        Optional<Session> optSession = Optional.fromNullable(session);
        if (!optSession.isPresent()) {
            SavedModelBundle modelBundle = SavedModelBundle.load(path, "serve");
            session = modelBundle.session();
            log.info("Starting TF session with model-wo-pre from: " + path);
        }
    }

    //TODO: add batch process support
    @Override
    public float[] forward(Path imagePath) {
        byte[][] pathTensor= new byte[1][];  // Remove 1 while adding batch support.
        Preconditions.checkNotNull(session, "Session cant be null");
        pathTensor[0] = imagePath.toString().getBytes(StandardCharsets.UTF_8);
        Tensor<String> imagePathTensor = Tensors.create(pathTensor);
        Tensor embTensor = session.runner()
                .fetch("embeddings")
                .feed("image_path_tensors", imagePathTensor)
                .run().get(0);  //If multiple fetches, it returns List<Tensor<?>>
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

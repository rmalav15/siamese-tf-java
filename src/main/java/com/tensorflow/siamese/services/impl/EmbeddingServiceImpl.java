package com.tensorflow.siamese.services.impl;

import com.google.common.base.Preconditions;
import com.tensorflow.siamese.services.EmbeddingService;
import com.tensorflow.siamese.services.TfModelServingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    @Autowired
    private TfModelServingService tfModelServingService;

    @Value("${tensorflow.model.path:src/main/resources/model}")
    private String path;

    @Value("${tensorflow.model-wo-pre.input.size:224}")
    private int imageSize;

    @Override
    public void startService() {
        //String modePath = getClass().getResource(path).getPath();
        tfModelServingService.initializeGraph(path);
    }

    @Override
    public List<Double> getEmbeddings(Path imagePath) {
        startService();
        Preconditions.checkNotNull(imagePath, "Image is null.");

        /*ImagePlus resImage = imageProcessingService.resizeImage(image, imageSize);
        Tensor imageTensor = imageProcessingService.converToTensor(resImage);*/

        float[] embeddings = tfModelServingService.forward(imagePath);
        return IntStream.range(0, embeddings.length)
                .mapToDouble(i -> embeddings[i])
                .boxed()
                .collect(Collectors.toList());
    }


    @Override
    public void closeService() {
        tfModelServingService.closeGraph();
    }
}

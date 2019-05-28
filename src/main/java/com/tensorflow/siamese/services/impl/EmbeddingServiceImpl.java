package com.tensorflow.siamese.services.impl;

import com.tensorflow.siamese.services.EmbeddingService;
import com.tensorflow.siamese.services.ImageProcessingService;
import com.tensorflow.siamese.services.TfModelServingService;
import ij.ImagePlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tensorflow.Tensor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    @Autowired
    private TfModelServingService tfModelServingService;

    @Autowired
    private ImageProcessingService imageProcessingService;

    @Value("${tensorflow.model.path:builder_model}")
    private String path;

    @Value("${tensorflow.model.input.size:224}")
    private int imageSize;

    @Override
    public void startService() {
        tfModelServingService.initializeGraph(path);
    }

    @Override
    public List<Double> getEmbeddings(ImagePlus image) {
        ImagePlus resImage = imageProcessingService.resizeImage(image, imageSize);
        Tensor imageTensor = imageProcessingService.converToTensor(resImage);
        double[] embeddings = tfModelServingService.forward(imageTensor);
        return DoubleStream.of(embeddings).boxed().collect(Collectors.toList());
    }


    @Override
    public void closeService() {
        tfModelServingService.closeGraph();
    }
}

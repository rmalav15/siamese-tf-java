package com.tensorflow.siamese.services;

import ij.ImagePlus;
import org.tensorflow.Tensor;

public interface ImageProcessingService {
    ImagePlus resizeImage(ImagePlus image, int size);

    Tensor converToTensor(ImagePlus image);
}

package com.tensorflow.siamese.services.impl;

import com.tensorflow.siamese.services.ImageProcessingService;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tensorflow.Tensor;

@Service
@Slf4j
public class ImageProcessingServiceImpl implements ImageProcessingService {

    @Override
    public ImagePlus resizeImage(ImagePlus image, int imageSize) {
        ImageProcessor imp = image.getProcessor();
        ImageProcessor resImp = imp.resize(imageSize, imageSize);
        image.setProcessor(resImp);
        return image;
    }

    @Override
    public Tensor converToTensor(ImagePlus image) {
        float imageArray[][][][] = convertToArray(image);
        Tensor imageTensor = Tensor.create(imageArray);
        return imageTensor;
    }

    private float[][][][] convertToArray(ImagePlus image) {
        int hieght = image.getHeight();
        int width = image.getWidth();
        float imageArray[][][][] = new float[1][hieght][width][3];

        ImageProcessor imp = image.getProcessor();
        int[] rgb = new int[3];
        for (int h = 0; h < hieght; h++) {
            for (int w = 0; w < width; w++) {
                imp.getPixel(h, w, rgb);
                imageArray[0][h][w][0] = rgb[0];
                imageArray[0][h][w][1] = rgb[1];
                imageArray[0][h][w][2] = rgb[2];
            }
        }
        return imageArray;
    }
}

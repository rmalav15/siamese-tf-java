package com.tensorflow.siamese.services.impl;

import com.tensorflow.siamese.services.ImageProcessingService;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tensorflow.Tensor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@Service
@Slf4j
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    @Override
    public ImagePlus resizeImage(ImagePlus image, int imageSize) {
        ImageProcessor imp = image.getProcessor();
        ImageProcessor resImp = imp.resize(imageSize, imageSize);
        image.setProcessor(resImp);
        return image;
    }

    @Override
    public Tensor converToTensor(ImagePlus image) {
        float[][][][] imageArray = convertToArray(image);
        Tensor imageTensor = Tensor.create(imageArray);
        return imageTensor;
    }

    private float[][][][] convertToArray(ImagePlus image) {
        int height = image.getHeight();
        int width = image.getWidth();
        float[][][][] imageArray = new float[1][height][width][3];

        BufferedImage bufferedImage = toBufferedImage(image.getImage());
        // displayImage(bufferedImage);

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                Color color = new Color(bufferedImage.getRGB(h, w));
                imageArray[0][h][w][0] = (float) (color.getRed() / 255.0);
                imageArray[0][h][w][1] = (float) (color.getGreen() / 255.0);
                imageArray[0][h][w][2] = (float) (color.getBlue() / 255.0);
            }
        }
        return imageArray;
    }

    private void displayImage(BufferedImage img) {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(224, 224);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

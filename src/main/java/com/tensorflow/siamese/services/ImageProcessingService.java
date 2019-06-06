package com.tensorflow.siamese.services;

import ij.ImagePlus;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.Tensor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface ImageProcessingService {
    ImagePlus resizeImage(ImagePlus image, int size);

    Tensor converToTensor(ImagePlus image);

    static Path write(MultipartFile file, String path) throws IOException {
        Path dir = Paths.get(path);
        Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());

        OutputStream os = Files.newOutputStream(filepath);
        os.write(file.getBytes());

        return filepath;
    }

    /*Tensor TfReadandResizeImage(Path imagePath, int size);*/
}

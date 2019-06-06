package com.tensorflow.siamese.services;

import java.nio.file.Path;

public interface RecognitionService {
    //TODO: add JavaDoc
    RecognitionResult recognise(Path imagePath, Double maxDistanceThr, Double firstSecondMarginGap) throws Exception;
}

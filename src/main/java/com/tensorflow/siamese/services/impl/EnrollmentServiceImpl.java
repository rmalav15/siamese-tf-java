package com.tensorflow.siamese.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensorflow.siamese.models.User;
import com.tensorflow.siamese.repositories.UserRepository;
import com.tensorflow.siamese.services.EmbeddingService;
import com.tensorflow.siamese.services.EnrollmentService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmbeddingService embeddingService;

    @Override
    public User enrollNew(@NonNull List<Path> images, @NonNull String name) throws JsonProcessingException, FileNotFoundException {
        checkImagesExist(images);
        List<List<Double>> embeddingsList = images
                .stream()
                .map(image -> embeddingService.getEmbeddings(image))
                .collect(Collectors.toList());
        List<Double> embedding = matrixMean(embeddingsList);

        User user = new User()
                .name(name)
                .numImages(images.size())
                .created(Instant.now())
                .embedding(objectMapper.writeValueAsString(embedding));
        userRepository.save(user);
        log.info("created new user: " + user.id());
        return user;
    }


    @Override
    public User updateEnrolled(@NonNull List<Path> images, @NonNull UUID uuid) throws Exception {
        checkImagesExist(images);
        User user = userRepository.getOne(uuid);
        if (null == user) {
            throw new Exception("user doesnt exist");
        }
        int savedNumImage = user.numImages();
        int extraImagesNum = images.size();

        List<Double> savedEmbeddings = objectMapper.readValue(user.embedding(), new TypeReference<List<Double>>() {
        });

        List<List<Double>> embeddingsList = images
                .stream()
                .map(image -> embeddingService.getEmbeddings(image))
                .collect(Collectors.toList());
        List<Double> embedding = matrixMean(embeddingsList);

        user.modified(Instant.now())
                .numImages(savedNumImage + extraImagesNum)
                .embedding(objectMapper.writeValueAsString(weightedMean(savedEmbeddings, savedNumImage
                        , embedding, extraImagesNum)));
        userRepository.save(user);
        log.info("updated user: " + user.id());
        return user;
    }

    private void checkImagesExist(List<Path> images) throws FileNotFoundException {
        for (Path imagePath : images) {
            File f = new File(imagePath.toUri());
            if (!(f.exists() && !f.isDirectory())) {
                throw new FileNotFoundException(imagePath.toString());
            }
        }
    }

    private List<Double> weightedMean(List<Double> emb1, int w1, List<Double> emb2, int w2) {
        List<Double> embeddings = new ArrayList<>();
        for (int r = 0; r < emb1.size(); r++) {
            embeddings.add((emb1.get(r) * w1 + emb2.get(r) * w1) / (w1 + w2));
        }
        return embeddings;
    }

    private List<Double> matrixMean(List<List<Double>> embeddingsList) {
        int cols = embeddingsList.size();
        int rows = embeddingsList.get(0).size();

        List<Double> embedding = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            List<Double> tmpList = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                tmpList.add(embeddingsList.get(c).get(r));
            }
            embedding.add(arrayMean(tmpList));
        }
        return embedding;
    }

    private Double arrayMean(List<Double> array) {
        return array.stream().mapToDouble(Double::doubleValue).sum() / array.size();
    }
}

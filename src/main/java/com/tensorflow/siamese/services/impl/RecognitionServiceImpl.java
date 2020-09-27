package com.tensorflow.siamese.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensorflow.siamese.models.User;
import com.tensorflow.siamese.repositories.UserRepository;
import com.tensorflow.siamese.services.EmbeddingService;
import com.tensorflow.siamese.services.RecognitionService;
import com.tensorflow.siamese.services.RecognitionResult;
import com.tensorflow.siamese.util.Pair;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class RecognitionServiceImpl implements RecognitionService {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmbeddingService embeddingService;

    private double euclideanDistance(List<Double> emb1, List<Double> emb2) throws Exception {
        if (emb1.size() != emb2.size()) {
            throw new Exception("Both embeddings should be of same length.");
        }
        double diff_square_sum = 0.0;
        for (int i = 0; i < emb1.size(); i++) {
            diff_square_sum += (emb1.get(i) - emb2.get(i)) * (emb1.get(i) - emb2.get(i));
        }
        return Math.sqrt(diff_square_sum);
    }

    @Override
    public RecognitionResult recognise(@NonNull Path imagePath, @NonNull Double maxDistanceThr
            , @NonNull Double firstSecondMarginGap) throws Exception {
        List<User> allUsers = userRepository.findAll();
        if (null == allUsers) {
            throw new Exception("No Users Enrolled.");
        }

        List<Double> imageEmb = embeddingService.getEmbeddings(imagePath);
        List<Pair<User, Double>> userMatches = new ArrayList<>();

        for (User user : allUsers) {
            List<Double> userEmb = objectMapper.readValue(user.embedding(), new TypeReference<List<Double>>() {
            });
            userMatches.add(new Pair<>(user, euclideanDistance(imageEmb, userEmb)));
        }

        Collections.sort(userMatches, Comparator.comparing(p -> p.getValue()));
        User bestMatch = null;
        double bestDistance = userMatches.get(0).getValue();

        if (bestDistance <= maxDistanceThr) {
            if (userMatches.size() == 1 || userMatches.get(1).getValue() - bestDistance >= firstSecondMarginGap) {
                bestMatch = userMatches.get(0).getKey();
            }
        }

        return new RecognitionResult(userMatches, bestMatch);
    }


}

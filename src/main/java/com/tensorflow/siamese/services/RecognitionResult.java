package com.tensorflow.siamese.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.tensorflow.siamese.models.User;
import com.tensorflow.siamese.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RecognitionResult {

    List<Pair<User, Double>> userList;

    User bestMatch;
}

package com.tensorflow.siamese.services;

import com.tensorflow.siamese.models.User;
import javafx.util.Pair;
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
public class RecogntionResult {

    List<Pair<User, Double>> userList;

    User bestMatch;
}

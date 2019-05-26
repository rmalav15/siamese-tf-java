package com.tensorflow.siamese.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {

    @Id
    @GeneratedValue
    @NonNull
    private UUID id;

    @NonNull
    @Column(unique = true, columnDefinition = "VARCHAR(45)")
    private String name;

    private int numImages;

    private String embedding;

    @NonNull
    private Instant created;

    private Instant modified;
}

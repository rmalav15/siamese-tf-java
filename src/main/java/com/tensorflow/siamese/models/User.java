package com.tensorflow.siamese.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @NonNull
    private UUID id;

    @NonNull
    @Column(unique = true, columnDefinition = "VARCHAR(45)")
    private String name;

    private int numImages;

    @Column(columnDefinition = "TEXT")
    private String embedding;

    @NonNull
    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;
}

package com.example.cricketApplication.models;

import jakarta.persistence.*;
import org.exolab.castor.types.DateTime;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String heading;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String link;

    @CreationTimestamp
    private LocalDateTime dateTime;

    private String author;
    private String createdBy;
    private DateTime createdOn;
    private String updatedBy;
    private DateTime updatedOn;

    // One news item can have multiple images
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

}

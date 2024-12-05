package com.example.cricketApplication.models;

import jakarta.persistence.*;
import org.exolab.castor.types.DateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    private String author;
    private String createdBy;
    @CreationTimestamp
    private LocalDateTime createdOn;
    private String updatedBy;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    // One news item can have multiple images
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

}

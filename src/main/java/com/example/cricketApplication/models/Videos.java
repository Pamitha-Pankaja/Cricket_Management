package com.example.cricketApplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@Entity
public class Videos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        private String url;

        @CreationTimestamp
        private LocalDateTime dateTime;
        private String createdBy;
        private Date createdOn;
        private String updatedBy;
        private Date updatedOn;

    }

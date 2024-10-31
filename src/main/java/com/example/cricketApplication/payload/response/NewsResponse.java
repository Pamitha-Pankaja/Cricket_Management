package com.example.cricketApplication.payload.response;

import com.example.cricketApplication.models.News;
import com.example.cricketApplication.models.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String heading;
    private String body;
    private List<String> images;
    private String link;
    private LocalDateTime dateTime;
    private String author;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;

    // Constructor to build a response from a News entity
    public NewsResponse(News news) {
        this.id = news.getId();
        this.heading = news.getHeading();
        this.body = news.getBody();
        this.images = news.getImages().stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
        this.link = news.getLink();
        this.dateTime = news.getDateTime();
        this.author = news.getAuthor();
        this.createdBy = news.getCreatedBy();
        this.createdOn = news.getCreatedOn();
        this.updatedBy = news.getUpdatedBy();
        this.updatedOn = news.getUpdatedOn();
    }
}

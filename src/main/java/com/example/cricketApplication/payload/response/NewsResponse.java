package com.example.cricketApplication.payload.response;

import com.example.cricketApplication.models.News;
import com.example.cricketApplication.models.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.exolab.castor.types.DateTime;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String heading;
    private String body;
    private String link;
    private String author;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private List<String> imageUrls; // Includes both imageId and URL
    // private List<ImageResponse> images;

    public NewsResponse(News news) {
        this.id = news.getId();
        this.heading = news.getHeading();
        this.body = news.getBody();
        this.link = news.getLink();
        this.author = news.getAuthor();
        this.updatedBy = news.getUpdatedBy();
        this.updatedOn = news.getUpdatedOn();
        this.createdOn = news.getCreatedOn();
        this.createdBy = news.getCreatedBy();

        // Generate URLs for images
        this.imageUrls = news.getImages().stream()
                .map(image -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(image.getImageUrl())
                        .toUriString())
                .collect(Collectors.toList());


//        // Generate URLs and map imageId
//        this.images = news.getImages().stream()
//                .map(image -> new ImageResponse(
//                        image.getId(),
//                        ServletUriComponentsBuilder.fromCurrentContextPath()
//                                .path("/images/")
//                                .path(image.getImageUrl())
//                                .toUriString()
//                ))
//                .collect(Collectors.toList());
    }
}

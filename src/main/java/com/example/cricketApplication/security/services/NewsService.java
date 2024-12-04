package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.Image;
import com.example.cricketApplication.models.News;
import com.example.cricketApplication.payload.response.NewsResponse;
import com.example.cricketApplication.repository.ImageRepository;
import com.example.cricketApplication.repository.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ImageRepository imageRepository;

    // Method to get all news and convert them to NewsResponse
    public List<NewsResponse> getAllNews() {
        return newsRepository.findAll().stream()
                .map(NewsResponse::new) // Convert each News entity to NewsResponse
                .collect(Collectors.toList());
    }

    public Optional<NewsResponse> getNewsById(Long id) {
        return newsRepository.findById(id).map(NewsResponse::new); // Convert News to NewsResponse
    }

    public NewsResponse createNews(News news) {
        if (news.getImages() != null) {
            news.getImages().forEach(image -> image.setNews(news));
        }
        News savedNews = newsRepository.save(news);
        return new NewsResponse(savedNews);
    }

//    @Transactional
//    public NewsResponse updateNews(Long newsId, News updatedNews) {
//        // Retrieve the existing news item
//        News existingNews = newsRepository.findById(newsId)
//                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + newsId));
//
//        // Update the details of the existing news with the new values
//        existingNews.setHeading(updatedNews.getHeading());
//        existingNews.setBody(updatedNews.getBody());
//        existingNews.setLink(updatedNews.getLink());
//        existingNews.setAuthor(updatedNews.getAuthor());
//        existingNews.setCreatedBy(updatedNews.getCreatedBy());
//        existingNews.setUpdatedBy(updatedNews.getUpdatedBy());
//        existingNews.setUpdatedOn(new Date());
//
//        // Handle the images
//        Set<Image> existingImages = new HashSet<>(existingNews.getImages());
//        Set<Image> newImages = updatedNews.getImages() != null ? updatedNews.getImages() : new HashSet<>();
//
//        // Remove images that are no longer present in the new set
//        existingImages.stream()
//                .filter(existingImage -> newImages.stream()
//                        .noneMatch(newImage -> newImage.getId() != null && newImage.getId().equals(existingImage.getId())))
//                .forEach(imageRepository::delete);
//
//        // Update or add each new image
//        newImages.forEach(newImage -> {
//            if (newImage.getId() != null) {
//                // Update the existing image
//                imageRepository.findById(newImage.getId()).ifPresent(existingImage -> {
//                    existingImage.setImageUrl(newImage.getImageUrl());
//                    imageRepository.save(existingImage);
//                });
//            } else {
//                // Add new image and associate it with the news
//                newImage.setNews(existingNews);
//                imageRepository.save(newImage);
//            }
//        });
//
//        // Save the updated news item with the modified images
//        News savedNews = newsRepository.save(existingNews);
//        return new NewsResponse(savedNews);
//    }
    @Transactional
    public NewsResponse updateNews(Long id, News newsDetails) {
        return newsRepository.findById(id).map(news -> {
            news.setHeading(newsDetails.getHeading());
            news.setBody(newsDetails.getBody());
            news.setLink(newsDetails.getLink());
            news.setDateTime(newsDetails.getDateTime());
            news.setUpdatedBy(newsDetails.getUpdatedBy());
            news.setUpdatedOn(newsDetails.getUpdatedOn());

            //deleteImagesByNewsId(id);
            imageRepository.deleteByNewsId(id);
            news.getImages().clear();
            // Add new images
            if (newsDetails.getImages() != null) {
                newsDetails.getImages().forEach(image -> {
                    image.setNews(news); // Associate each new image with this news item
                    news.getImages().add(image);
                });
            }

            News Updatednews = newsRepository.save(news);
            return new NewsResponse(Updatednews);

        }).orElseThrow(() -> new EntityNotFoundException("News not found with id: " + id));
    }

    @Transactional
    public void deleteImagesByNewsId(Long newsId) {
        imageRepository.deleteByNewsId(newsId);
    }


    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}

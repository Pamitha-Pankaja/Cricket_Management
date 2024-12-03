package com.example.cricketApplication.controllers;

import com.example.cricketApplication.models.News;
import com.example.cricketApplication.payload.response.MessageResponse;
import com.example.cricketApplication.payload.response.NewsResponse;
import com.example.cricketApplication.security.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<NewsResponse> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id)
                .map(ResponseEntity::ok) // Return 200 OK with the NewsResponse
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found
    }

    @PostMapping
    public NewsResponse createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable Long newsId, @RequestBody News updatedNews) {
        NewsResponse newsResponse = newsService.updateNews(newsId, updatedNews);
        return ResponseEntity.ok(newsResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/deleteImages/{newsId}")
    public ResponseEntity<?> deleteImages(@PathVariable Long newsId) {
        // Call the service method to delete images
        newsService.deleteImagesByNewsId(newsId);
        return ResponseEntity.ok(new MessageResponse("Images deleted successfully."));
    }

}
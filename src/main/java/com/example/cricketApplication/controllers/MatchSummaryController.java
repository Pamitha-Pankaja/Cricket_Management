package com.example.cricketApplication.controllers;
import com.example.cricketApplication.exceptions.InvalidMatchSummaryException;
import com.example.cricketApplication.exceptions.MatchNotFoundException;
import com.example.cricketApplication.models.MatchSummary;
import com.example.cricketApplication.payload.response.MatchSummaryResponse;
import com.example.cricketApplication.payload.response.MessageResponse;

import com.example.cricketApplication.security.services.MatchSummaryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/matchSummary")
public class MatchSummaryController {

    @Autowired
    private MatchSummaryService matchSummaryService;


    @PostMapping("/add")
    public MatchSummary createMatchSummary(@RequestBody MatchSummary matchSummary) {
        return matchSummaryService.createMatchSummary(matchSummary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMatchSummaryById(@PathVariable Long id) {
        Optional<MatchSummary> matchSummary = matchSummaryService.getMatchSummaryById(id);
        if (matchSummary.isPresent()) {
            return ResponseEntity.ok(matchSummary.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error: MatchSummary not found"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MatchSummary>> getAllMatchSummaries() {
        List<MatchSummary> matchSummaries = matchSummaryService.getAllMatchSummaries();
        return ResponseEntity.ok(matchSummaries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMatchSummaryById(@PathVariable Long id) {
        try {
            matchSummaryService.deleteMatchSummaryById(id);
            return ResponseEntity.ok(new MessageResponse("MatchSummary deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<MatchSummaryResponse>> getMatchSummariesByMatchId(@PathVariable Long matchId) {
        List<MatchSummaryResponse> matchSummaries = matchSummaryService.getMatchSummariesByMatchId(matchId);
        if (!matchSummaries.isEmpty()) {
            return ResponseEntity.ok(matchSummaries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ArrayList<>()); // or some meaningful error response
        }
    }
}


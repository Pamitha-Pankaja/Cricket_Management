package com.example.cricketApplication.controllers;

import com.example.cricketApplication.models.Match;
import com.example.cricketApplication.payload.response.MatchResponse;
import com.example.cricketApplication.security.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    // Add a new match
    @PostMapping("/add")
    public ResponseEntity<Match> addMatch(@RequestBody Match match) {
        Match savedMatch = matchService.saveMatch(match);
        return ResponseEntity.ok(savedMatch);
    }

    // Get a match by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
//        Optional<Match> match = matchService.getMatchById(id);
//        return match.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MatchResponse>> getMatchById(@PathVariable Long id) {
        Match match = matchService.getMatchById(id);
                //.orElseThrow(() -> new RuntimeException("Match not found with id: " + id));

        // Wrap the match in a list since RefactorResponse expects a list
        List<Match> matchList = Collections.singletonList(match);

        // Call RefactorResponse to convert the match to a MatchResponse
        List<MatchResponse> matchResponse = matchService.RefactorResponse(matchList);

        return ResponseEntity.ok(matchResponse);
    }



    // Get all matches
//    @GetMapping("/all")
//    public ResponseEntity<List<Match>> getAllMatches() {
//        List<Match> matches = matchService.getAllMatches();
//        return ResponseEntity.ok(matches);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<MatchResponse>> getAllMatches() {
        List<MatchResponse> matchResponses = matchService.getAllMatches();
        return ResponseEntity.ok(matchResponses);
    }


    // Delete a match by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MatchResponse> updateMatch(@PathVariable Long id, @RequestBody Match matchDetails) {
        MatchResponse updatedMatch = matchService.updateMatch(id, matchDetails);
        return ResponseEntity.ok(updatedMatch);
    }

}


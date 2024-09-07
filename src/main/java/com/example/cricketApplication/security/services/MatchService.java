package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.Match;
import com.example.cricketApplication.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    // Save a new match
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    // Get a match by ID
    public Optional<Match> getMatchById(Long matchId) {
        return matchRepository.findById(matchId);
    }

    // Get all matches
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    // Delete a match by ID
    public void deleteMatch(Long matchId) {
        matchRepository.deleteById(matchId);
    }
}

package com.example.cricketApplication.security.services;

import com.example.cricketApplication.controllers.MatchSummaryController;
import com.example.cricketApplication.exceptions.InvalidMatchSummaryException;
import com.example.cricketApplication.exceptions.MatchNotFoundException;
import com.example.cricketApplication.models.Match;
import com.example.cricketApplication.models.MatchSummary;
import com.example.cricketApplication.payload.response.MatchResponse;
import com.example.cricketApplication.payload.response.MatchSummaryResponse;
import com.example.cricketApplication.repository.MatchRepository;
import com.example.cricketApplication.repository.MatchSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchSummaryService {

    @Autowired
    private MatchSummaryRepository matchSummaryRepository;

    @Autowired
    private MatchRepository matchRepository;


    public MatchSummary createMatchSummary(MatchSummary matchSummary) {
        return matchSummaryRepository.save(matchSummary);
    }


    public Optional<MatchSummary> getMatchSummaryById(Long id) {
        return matchSummaryRepository.findById(id);
    }

    public List<MatchSummary> getAllMatchSummaries() {
        return matchSummaryRepository.findAll();
    }

    public void deleteMatchSummaryById(Long id) {
        matchSummaryRepository.deleteById(id);
    }

    // New method to get all match summaries by match ID
    public List<MatchSummaryResponse> getMatchSummariesByMatchId(Long matchId) {
        List<MatchSummary> matchSummaryList = matchSummaryRepository.findByMatchMatchId(matchId);
        return RefactorResponse(matchSummaryList);
    }

    private List<MatchSummaryResponse> RefactorResponse(List<MatchSummary> matchSummaryList) {
        List<MatchSummaryResponse> matchSummaryResponseList = new ArrayList<>();
        for (MatchSummary matchSummary : matchSummaryList) {
            MatchSummaryResponse response = new MatchSummaryResponse();
            response.setId(matchSummary.getId());
            response.setInning(matchSummary.getInning());
            response.setRuns(matchSummary.getRuns());
            response.setWickets(matchSummary.getWickets());
            response.setOvers(matchSummary.getOvers());
            response.setOppositionRuns(matchSummary.getOppositionRuns());
            response.setOppositionWickets(matchSummary.getOppositionWickets());
            response.setOppositionOvers(matchSummary.getOppositionOvers());
            response.setResult(matchSummary.getResult());
            response.setMatchId(matchSummary.getMatch().getMatchId());// Get the match ID

            response.setMatchId(matchSummary.getMatch().getMatchId());  // Get the match ID
            response.setVenue(matchSummary.getMatch().getVenue());  // Get the match venue
            response.setOpposition(matchSummary.getMatch().getOpposition());  // Get the opposition team
            matchSummaryResponseList.add(response);




        }
        return matchSummaryResponseList;
    }
}


package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.Match;
import com.example.cricketApplication.models.Player;
import com.example.cricketApplication.models.PlayerStats;
import com.example.cricketApplication.payload.response.MatchResponse;
import com.example.cricketApplication.payload.response.PlayerResponse;
import com.example.cricketApplication.payload.response.PlayerStatsResponse;
import com.example.cricketApplication.repository.MatchRepository;
import com.example.cricketApplication.repository.PlayerRepository;
import com.example.cricketApplication.repository.PlayerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerStatsService {

    @Autowired
    private PlayerStatsRepository playerStatsRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerStats createPlayerStats(PlayerStats playerStats) {
        return playerStatsRepository.save(playerStats);
    }

    public Optional<PlayerStats> getPlayerStatsById(Long id) {
        return playerStatsRepository.findById(id);
    }

    public List<PlayerStats> getAllPlayerStats() {
        return playerStatsRepository.findAll();
    }

    public List<PlayerStats> getPlayerStatsByPlayerId(Long playerId) {
        return playerStatsRepository.findByPlayer_PlayerId(playerId);
    }

    public List<PlayerStats> getPlayerStatsByMatchId(Long matchId) {
        return playerStatsRepository.findByMatch_MatchId(matchId);
    }

    public void deletePlayerStatsById(Long id) {
        playerStatsRepository.deleteById(id);
    }

//    public List<PlayerStats> getPlayerStatsByMatch(Long playerId, Long matchId) {
//        Player player = playerRepository.findById(playerId)
//                .orElseThrow();
//
//        Match match = matchRepository.findById(matchId)
//                .orElseThrow();
//
//        return playerStatsRepository.findByPlayerAndMatch(player, match);
//    }

    public List<PlayerStatsResponse> getPlayerStatsByMatch(Long playerId, Long matchId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow();
                        //-> new ResourceNotFoundException("Player not found with id " + playerId));
        Match match = matchRepository.findById(matchId)
                .orElseThrow() ;
        //-> new ResourceNotFoundException("Match not found with id " + matchId));

        List<PlayerStats> playerStatsList = playerStatsRepository.findByPlayerAndMatch(player, match);
        return RefactorResponse(playerStatsList);
    }


    public List<PlayerStatsResponse> getPlayerStatsByMatchType(Long playerId, String matchType) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow();
                        //-> new ResourceNotFoundException("Player not found with id " + playerId));

        List<PlayerStats> playerStatsList = playerStatsRepository.findByPlayerAndMatch_Type(player, matchType);
        return RefactorResponse(playerStatsList);
    }





    private List<PlayerStatsResponse> RefactorResponse(List<PlayerStats> playerStatsList) {
        List<PlayerStatsResponse> playerStatsResponseList = new ArrayList<>();
        for (PlayerStats playerStats : playerStatsList) {
            PlayerStatsResponse playerStatsResponse = new PlayerStatsResponse();
            playerStatsResponse.setId(playerStats.getId());
            playerStatsResponse.setBalls(playerStats.getBalls());
            playerStatsResponse.setCenturies(playerStats.getCenturies());
            playerStatsResponse.setInning(playerStats.getInning());
            playerStatsResponse.setFifties(playerStats.getFifties());
            playerStatsResponse.setFours(playerStats.getFours());
            playerStatsResponse.setSixers(playerStats.getSixers());
            playerStatsResponse.setOvers(playerStats.getOvers());
            playerStatsResponse.setRuns(playerStats.getRuns());
            playerStatsResponse.setSixers(playerStats.getSixers());
            playerStatsResponse.setWickets(playerStats.getWickets());
            playerStatsResponse.setRunsConceded(playerStats.getRunsConceded());

//            MatchResponse matchResponse = new MatchResponse();
//            matchResponse.setMatchId(playerStats.getMatch().getMatchId());
//
//
//            PlayerResponse playerResponse = new PlayerResponse();
//            playerResponse.setPlayerId(playerStats.getPlayer().getPlayerId());

            playerStatsResponseList.add(playerStatsResponse);

        }
        return playerStatsResponseList;
    }

}

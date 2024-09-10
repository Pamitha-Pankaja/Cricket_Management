package com.example.cricketApplication.repository;

import com.example.cricketApplication.models.Match;
import com.example.cricketApplication.models.Player;
import com.example.cricketApplication.models.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
//    List<PlayerStats> findStatsByPlayerId(Long playerId);
//    List<PlayerStats> findByPlayer_Id(Long playerId);
    List<PlayerStats> findByPlayer_PlayerId(Long playerId);
    List<PlayerStats> findByMatch_MatchId(Long matchId);

    List<PlayerStats> findByPlayerAndMatch(Player player, Match match);
    List<PlayerStats> findByPlayerAndMatch_Type(Player player, String matchType);
    List<PlayerStats> findByMatch(Match match);
}

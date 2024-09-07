package com.example.cricketApplication.repository;


import com.example.cricketApplication.models.MatchSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchSummaryRepository extends JpaRepository<MatchSummary, Long> {
    // You can add custom query methods if needed

    List<MatchSummary> findByMatchMatchId(Long matchId);
}

package com.example.cricketApplication.repository;

import com.example.cricketApplication.models.HistoricalPlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalPlayerStatsRepository extends JpaRepository<HistoricalPlayerStats, Long> {
}

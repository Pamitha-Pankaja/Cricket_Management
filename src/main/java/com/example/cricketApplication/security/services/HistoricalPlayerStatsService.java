package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.HistoricalPlayerStats;
import com.example.cricketApplication.repository.HistoricalPlayerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricalPlayerStatsService {

    private final HistoricalPlayerStatsRepository statsRepository;

    @Autowired
    public HistoricalPlayerStatsService(HistoricalPlayerStatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public HistoricalPlayerStats addHistoricalPlayerStats(HistoricalPlayerStats stats) {
        return statsRepository.save(stats);
    }

    public HistoricalPlayerStats getHistoricalPlayerStatsById(Long id) {
        return statsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HistoricalPlayerStats not found with id: " + id));
    }

    public List<HistoricalPlayerStats> getAllHistoricalPlayerStats() {
        return statsRepository.findAll();
    }

    public void deleteHistoricalPlayerStats(Long id) {
        if (statsRepository.existsById(id)) {
            statsRepository.deleteById(id);
        } else {
            throw new RuntimeException("HistoricalPlayerStats not found with id: " + id);
        }
    }

    public HistoricalPlayerStats updateHistoricalPlayerStats(Long id, HistoricalPlayerStats updatedStats) {
        return statsRepository.findById(id).map(stats -> {
            stats.setInning(updatedStats.getInning());
            stats.setRuns(updatedStats.getRuns());
            stats.setWickets(updatedStats.getWickets());
            stats.setFours(updatedStats.getFours());
            stats.setSixers(updatedStats.getSixers());
            stats.setFifties(updatedStats.getFifties());
            stats.setCenturies(updatedStats.getCenturies());
            stats.setBalls(updatedStats.getBalls());
            stats.setOvers(updatedStats.getOvers());
            stats.setRunsConceded(updatedStats.getRunsConceded());
            stats.setMatchType(updatedStats.getMatchType());
            stats.setUpdatedBy(updatedStats.getUpdatedBy());
            stats.setUpdatedOn(updatedStats.getUpdatedOn());
            return statsRepository.save(stats);
        }).orElseThrow(() -> new RuntimeException("HistoricalPlayerStats not found with id: " + id));
    }
}

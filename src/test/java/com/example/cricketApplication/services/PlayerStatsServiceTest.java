package com.example.cricketApplication.services;

import com.example.cricketApplication.models.Match;
import com.example.cricketApplication.models.Player;
import com.example.cricketApplication.models.PlayerStats;
import com.example.cricketApplication.payload.response.PlayerStatsResponse;
import com.example.cricketApplication.repository.MatchRepository;
import com.example.cricketApplication.repository.PlayerRepository;
import com.example.cricketApplication.repository.PlayerStatsRepository;
import com.example.cricketApplication.security.services.PlayerStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlayerStatsServiceTest {

    @InjectMocks
    private PlayerStatsService playerStatsService;

    @Mock
    private PlayerStatsRepository playerStatsRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private PlayerRepository playerRepository;

    private Player player;
    private Match match;
    private PlayerStats playerStats;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize dummy data
        player = new Player();
        player.setPlayerId(1L);
        player.setName("John Doe");

        match = new Match();
        match.setMatchId(1L);
        match.setType("ODI");

        playerStats = new PlayerStats();
        playerStats.setId(1L);
        playerStats.setPlayer(player);
        playerStats.setMatch(match);
        playerStats.setRuns(50);
    }

    @Test
    public void testGetPlayerStatsByMatch() {
        // Arrange: Setup the repository mocks to return the mock data
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(playerStatsRepository.findByPlayerAndMatch(player, match)).thenReturn(Arrays.asList(playerStats));

        // Act: Call the method under test
        List<PlayerStatsResponse> playerStatsResponses = playerStatsService.getPlayerStatsByMatch(1L, 1L);

        // Assert: Check if the output is as expected
        assertEquals(1, playerStatsResponses.size());
        assertEquals(50, playerStatsResponses.get(0).getRuns());

        // Verify the interactions with the mocks
        verify(playerRepository, times(1)).findById(1L);
        verify(matchRepository, times(1)).findById(1L);
        verify(playerStatsRepository, times(1)).findByPlayerAndMatch(player, match);
    }
}

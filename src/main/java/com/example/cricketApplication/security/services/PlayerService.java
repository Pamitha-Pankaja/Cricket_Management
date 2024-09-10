package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.Player;
import com.example.cricketApplication.models.PlayerStats;
import com.example.cricketApplication.payload.response.PlayerResponse;
import com.example.cricketApplication.payload.response.PlayerStatsResponse;
import com.example.cricketApplication.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player updatePlayer(Long id, Player playerDetails) {
        Player player = getPlayerById(id);
        player.setName(playerDetails.getName());
        player.setEmail(playerDetails.getEmail());
        player.setContactNo(playerDetails.getContactNo());
        player.setBattingStyle(playerDetails.getBattingStyle());
        player.setBowlingStyle(playerDetails.getBowlingStyle());
        player.setStatus(playerDetails.getStatus());
//        player.setUnder(playerDetails.getUnder());
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }


    private List<PlayerResponse> RefactorResponse(List<Player> playerList) {
        List<PlayerResponse> playerResponseList = new ArrayList<>();
        for (Player player : playerList) {
            PlayerResponse playerResponse = new PlayerResponse();
            playerResponse.setName(player.getName());
            playerResponse.setEmail(player.getEmail());
            playerResponse.setContactNo(player.getContactNo());
            playerResponse.setBattingStyle(player.getBattingStyle());
            playerResponse.setBowlingStyle(player.getBowlingStyle());
            playerResponse.setStatus(player.getStatus());

            playerResponseList.add(playerResponse);
        }
        return playerResponseList;
    }
}


package com.example.cricketApplication.controllers;

import com.example.cricketApplication.models.Player;
import com.example.cricketApplication.payload.response.MessageResponse;
import com.example.cricketApplication.payload.response.PlayerResponse;
import com.example.cricketApplication.security.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/add")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player savedPlayer = playerService.savePlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlayer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable Long id) {
        PlayerResponse playerResponse = playerService.getPlayerResponseById(id);
        return ResponseEntity.ok(playerResponse);
    }

    //    @GetMapping("/all")
//    public ResponseEntity<List<Player>> getAllPlayers() {
//        List<Player> players = playerService.getAllPlayers();
//        return ResponseEntity.ok(players);
//    }
    @GetMapping("/all")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
        List<PlayerResponse> players = playerService.getAllPlayerResponses();
        return ResponseEntity.ok(players);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable Long id, @RequestBody Player playerDetails) {
        PlayerResponse updatedPlayer = playerService.updatePlayer(id, playerDetails);
        return ResponseEntity.ok(updatedPlayer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}



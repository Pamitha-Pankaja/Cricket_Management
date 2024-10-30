package com.example.cricketApplication.security.services;

import com.example.cricketApplication.exceptions.PlayerAlreadyExistsException;
import com.example.cricketApplication.exceptions.PlayerNotFoundException;
import com.example.cricketApplication.models.Player;
import com.example.cricketApplication.models.PlayerStats;
import com.example.cricketApplication.models.Team;
import com.example.cricketApplication.models.User;
import com.example.cricketApplication.payload.response.PlayerResponse;
import com.example.cricketApplication.payload.response.PlayerResponseWithTeamDetails;
import com.example.cricketApplication.payload.response.PlayerStatsResponse;
import com.example.cricketApplication.repository.MembershipRepository;
import com.example.cricketApplication.repository.PlayerRepository;
import com.example.cricketApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    public Player savePlayer(Player player) {
        if (playerRepository.existsByEmail(player.getEmail())) {
            throw new PlayerAlreadyExistsException("Player with email " + player.getEmail() + " already exists.");
        }

        // You can add more validation logic here and throw InvalidPlayerDataException
        return playerRepository.save(player);
    }

    public PlayerResponse getPlayerResponseById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + id));
        return refactorResponse(player);
    }

//    public List<Player> getAllPlayers() {
//        return playerRepository.findAll();
//    }
    public List<PlayerResponse> getAllPlayerResponses() {
    List<Player> players = playerRepository.findAll();
    return players.stream()
            .map(this::refactorResponse)  // Convert each Player to PlayerResponse
            .collect(Collectors.toList());
    }

    public PlayerResponse updatePlayer(Long id, Player playerDetails) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + id));

        User user = player.getUser();

        // Check if the email is already taken by another user
        if (userRepository.existsByEmail(playerDetails.getUser().getEmail())
                && !user.getEmail().equals(playerDetails.getUser().getEmail())) {
            throw new PlayerAlreadyExistsException("Player with email " + playerDetails.getUser().getEmail() + " already exists.");
        }

        // Check if the username is already taken by another user
        if (userRepository.existsByUsername(playerDetails.getUser().getUsername())
                && !user.getUsername().equals(playerDetails.getUser().getUsername())) {
            throw new PlayerAlreadyExistsException("Plyer with username " + playerDetails.getUser().getUsername() + " already exists.");
        }

        // Ensure the coachDetails contains user information before updating
        if (playerDetails.getUser() != null) {
            // Update the username if provided in the request body
            if (playerDetails.getUser().getUsername() != null && !playerDetails.getUser().getUsername().isEmpty()) {
                user.setUsername(playerDetails.getUser().getUsername());
            }

            // Update the email if provided in the request body
            if (playerDetails.getUser().getEmail() != null && !playerDetails.getUser().getEmail().isEmpty()) {
                user.setEmail(playerDetails.getUser().getEmail());
            }

            // Update the password if provided in the request body and encode it
            if (playerDetails.getUser().getPassword() != null && !playerDetails.getUser().getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(playerDetails.getUser().getPassword());
                user.setPassword(encodedPassword);
            }

            // Save the updated user
            userRepository.save(user);
        }
        // Update player details
        player.setName(playerDetails.getName());
        player.setDateOfBirth(playerDetails.getDateOfBirth());
        player.setEmail(playerDetails.getUser().getEmail());
        player.setContactNo(playerDetails.getContactNo());
        player.setBattingStyle(playerDetails.getBattingStyle());
        player.setBowlingStyle(playerDetails.getBowlingStyle());
        player.setStatus(playerDetails.getStatus());
        player.setImage(playerDetails.getImage());
        player.setPlayerRole(playerDetails.getPlayerRole());
        player.setMembership(playerDetails.getMembership());
        player.setUpdatedOn(playerDetails.getUpdatedOn());
        player.setUpdatedBy(playerDetails.getUpdatedBy());
        membershipRepository.save(playerDetails.getMembership());

        Player updatedPlayer = playerRepository.save(player);

        return refactorResponse(updatedPlayer);
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + id));
        playerRepository.delete(player);
        userRepository.delete(player.getUser());
    }

    private PlayerResponse refactorResponse(Player player) {
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setPlayerId(player.getPlayerId());
        playerResponse.setName(player.getName());
        playerResponse.setDateOfBirth(player.getDateOfBirth());
        playerResponse.setEmail(player.getEmail());
        playerResponse.setContactNo(player.getContactNo());
        playerResponse.setBattingStyle(player.getBattingStyle());
        playerResponse.setBowlingStyle(player.getBowlingStyle());
        playerResponse.setStatus(player.getStatus());
        playerResponse.setImage(player.getImage());
        playerResponse.setPlayerRole(player.getPlayerRole());
        playerResponse.setStartDate(String.valueOf(player.getMembership().getStartDate()));
        playerResponse.setEndDate(String.valueOf(player.getMembership().getEndDate()));
        playerResponse.setPassword(player.getUser().getPassword());
        playerResponse.setUsername(player.getUser().getUsername());
        playerResponse.setMembershipId(player.getMembership().getId());
        playerResponse.setMembershipStartDate(player.getMembership().getStartDate());
        playerResponse.setMembershipEndDate(player.getMembership().getEndDate());

        // Populate team details using PlayerResponseWithTeamDetails
        List<PlayerResponseWithTeamDetails> teamDetails = player.getTeams().stream()
                .map(team -> new PlayerResponseWithTeamDetails(team.getUnder(), String.valueOf(team.getYear())))
                .collect(Collectors.toList());
        playerResponse.setTeamDetails(teamDetails);

        return playerResponse;
    }
}



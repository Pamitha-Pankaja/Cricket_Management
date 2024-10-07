package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.Coach;
import com.example.cricketApplication.models.Team;
import com.example.cricketApplication.models.User;
import com.example.cricketApplication.payload.response.CoachResponse;
import com.example.cricketApplication.payload.response.TeamResponse;
import com.example.cricketApplication.repository.CoachRepository;
import com.example.cricketApplication.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Coach addCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    public Optional<Coach> getCoachById(Long coachId) {
        return coachRepository.findById(coachId);
    }

//    public List<Coach> getAllCoaches() {
//        return coachRepository.findAll();
//    }

    public List<CoachResponse> getAllCoaches() {
        List<Coach> coach = coachRepository.findAll();
        return RefactorResponse(coach);  // Convert to MatchResponse list
    }



    public Optional<Coach> getCoachByUserId(Long userId) {
        return coachRepository.findByUser_Id(userId);
    }

    public List<Coach> getCoachesByRoleId(Long roleId) {
        return coachRepository.findByRole_Id(roleId);
    }

    public void deleteCoachById(Long coachId) {
        coachRepository.deleteById(coachId);
    }


    public CoachResponse updateCoach(Long coachId, Coach coachDetails) throws EntityNotFoundException {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException("Coach not found with ID: " + coachId));

        // Get and update the associated user (username, email, password)
        User user = coach.getUser();
        if (coachDetails.getUser() != null) {
            if (coachDetails.getUser().getUsername() != null) {
                user.setUsername(coachDetails.getUser().getUsername());
            }
            if (coachDetails.getUser().getEmail() != null) {
                user.setEmail(coachDetails.getUser().getEmail());
            }
            if (coachDetails.getUser().getPassword() != null) {
                String encodedPassword = passwordEncoder.encode(coachDetails.getUser().getPassword());
                user.setPassword(encodedPassword);
            }
        }

        // Update coach details
        coach.setImage(coachDetails.getImage());
        coach.setDateOfBirth(coachDetails.getDateOfBirth());
        coach.setName(coachDetails.getName());
        coach.setAddress(coachDetails.getAddress());
        coach.setDescription(coachDetails.getDescription());
        coach.setContactNo(coachDetails.getContactNo());

        // Save user and coach
        userRepository.save(user);
        Coach updatedCoach = coachRepository.save(coach);

        // Return the updated response
        return RefactorResponse(Collections.singletonList(updatedCoach)).get(0);
    }

    private List<CoachResponse> RefactorResponse(List<Coach> coaches) {
        List<CoachResponse> coachResponses = new ArrayList<>();
        for (Coach coach : coaches) {
            CoachResponse coachResponse = new CoachResponse();
            coachResponse.setCoachId(coach.getCoachId());
            coachResponse.setName(coach.getName());
            coachResponse.setEmail(coach.getEmail());
            coachResponse.setContactNo(coach.getContactNo());
            coachResponse.setAddress(coach.getAddress());
            coachResponse.setDateOfBirth(coach.getDateOfBirth());
            coachResponse.setImage(coach.getImage());
            coachResponse.setDescription(coach.getDescription());
            coachResponses.add(coachResponse);
        }
        return coachResponses;
    }
}


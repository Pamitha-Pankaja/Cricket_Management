package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.PractiseSession;
import com.example.cricketApplication.models.Team;
import com.example.cricketApplication.repository.PractiseSessionRepository;
import com.example.cricketApplication.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PractiseSessionService {

    @Autowired
    private PractiseSessionRepository practiseSessionRepository;

    @Autowired
    private TeamRepository teamRepository;

//    public PractiseSession addPractiseSession(PractiseSession practiseSession) {
//        return practiseSessionRepository.save(practiseSession);
//    }

    public PractiseSession addPractiseSession(PractiseSession practiseSession) {
        // Fetch the team based on the teamId from the practiseSession
        Team team = teamRepository.findById(practiseSession.getTeam().getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // Set the team for the practise session
        practiseSession.setTeam(team);

        // Save and return the practice session
        return practiseSessionRepository.save(practiseSession);
    }

    public List<PractiseSession> getAllPractiseSessions() {
        return practiseSessionRepository.findAll();
    }

    public Optional<PractiseSession> getPractiseSessionById(Long pracId) {
        return practiseSessionRepository.findById(pracId);
    }

//    public List<PractiseSession> getPractiseSessionsByCoachId(Long coachId) {
//        return practiseSessionRepository.findByAssignedCoach_CoachId(coachId);
//    }

//    public List<PractiseSession> getPractiseSessionsByUnder(String under) {
//        return practiseSessionRepository.findByUnder(under);
//    }

    public void deletePractiseSessionById(Long pracId) {
        practiseSessionRepository.deleteById(pracId);
    }
}


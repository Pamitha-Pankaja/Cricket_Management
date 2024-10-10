package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.PractiseSession;
import com.example.cricketApplication.models.Team;
import com.example.cricketApplication.payload.response.PracticeSessionResponse;
import com.example.cricketApplication.repository.PractiseSessionRepository;
import com.example.cricketApplication.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    // Method to get all practice sessions for a specific coach ID
    public List<PracticeSessionResponse> getPractiseSessionsByCoachId(Long coachId) {
        List<PractiseSession> practiceSessions =  practiseSessionRepository.findAllByCoachId(coachId);
        return RefactorResponse(practiceSessions);
    }

    public List<PracticeSessionResponse> RefactorResponse(List<PractiseSession> PractiseSessionList) {
        List<PracticeSessionResponse> practiceSessionResponseList = new ArrayList<>();
        for (PractiseSession practiceSession : PractiseSessionList){
            PracticeSessionResponse practiceSessionResponse = new PracticeSessionResponse();
            practiceSessionResponse.setPracId(practiceSession.getPracId());
            practiceSessionResponse.setVenue(practiceSession.getVenue());
            practiceSessionResponse.setDate(practiceSession.getDate());
            practiceSessionResponse.setStarTime(practiceSession.getStarTime());
            practiceSessionResponse.setEndTime(practiceSession.getEndTime());
            practiceSessionResponse.setPracType(practiceSession.getPracType());
            practiceSessionResponse.setTeamUnder(practiceSession.getTeam().getUnder());
            practiceSessionResponseList.add(practiceSessionResponse);
        }
        return practiceSessionResponseList;
    }
}


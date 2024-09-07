package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.PractiseSession;
import com.example.cricketApplication.repository.PractiseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PractiseSessionService {

    @Autowired
    private PractiseSessionRepository practiseSessionRepository;

    public PractiseSession addPractiseSession(PractiseSession practiseSession) {
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


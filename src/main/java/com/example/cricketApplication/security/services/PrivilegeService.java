package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.Coach;
import com.example.cricketApplication.models.Privilege;
import com.example.cricketApplication.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;


    public Privilege addPrivilege(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    public Optional<Privilege> getPrivilegeId(Long privilegeId) {
        return privilegeRepository.findById(privilegeId);
    }

//    public List<Coach> getAllCoaches() {
//        return coachRepository.findAll();
//    }
}

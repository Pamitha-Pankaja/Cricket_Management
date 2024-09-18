package com.example.cricketApplication.controllers;

import com.example.cricketApplication.models.Coach;
import com.example.cricketApplication.models.Privilege;
import com.example.cricketApplication.payload.response.MessageResponse;
import com.example.cricketApplication.security.services.CoachService;
import com.example.cricketApplication.security.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/privileges")
public class PrivilegesController {

    @Autowired
    private PrivilegeService privilegeService;

    @PostMapping("/add")
    public ResponseEntity<Privilege> addCoach(@RequestBody Privilege privilege) {
        Privilege privilege1 = privilegeService.addPrivilege(privilege);
        return ResponseEntity.ok(privilege1);
    }

    @GetMapping("/{coachId}")
    public ResponseEntity<?> getCoachById(@PathVariable Long coachId) {
        Optional<Privilege> coach = privilegeService.getPrivilegeId(coachId);
        if (coach.isPresent()) {
            return ResponseEntity.ok(coach.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error: Coach not found"));
        }
    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Coach>> getAllCoaches() {
//        List<Privilege> coaches = privilegeService.get();
//        return ResponseEntity.ok(coaches);
//    }
}

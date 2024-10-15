package com.example.cricketApplication.controllers;

import com.example.cricketApplication.models.ERole;
import com.example.cricketApplication.models.Official;
import com.example.cricketApplication.models.User;
import com.example.cricketApplication.payload.request.SignupRequest;
import com.example.cricketApplication.payload.response.MessageResponse;
import com.example.cricketApplication.repository.RoleRepository;
import com.example.cricketApplication.repository.UserRepository;
import com.example.cricketApplication.security.services.OfficialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/officials")
public class OfficialController {

    @Autowired
    private OfficialService officialService;



    // Get all officials
    @GetMapping("/all")
    public List<Official> getAllOfficials() {
        return officialService.getAllOfficials();
    }

    // Get official by id
    @GetMapping("/{id}")
    public Official getOfficialById(@PathVariable Long id) {
        return officialService.getOfficialById(id);
    }

    // Update official
    @PutMapping("/{id}")
    public Official updateOfficial(@PathVariable Long id, @RequestBody Official official) {
        return officialService.updateOfficial(id, official);
    }

    // Delete official
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOfficial(@PathVariable Long id) {
        officialService.deleteOfficial(id);
        return ResponseEntity.ok(new MessageResponse("Official deleted successfully!"));
    }
}



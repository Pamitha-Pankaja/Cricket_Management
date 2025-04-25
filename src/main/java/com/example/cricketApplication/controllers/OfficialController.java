package com.example.cricketApplication.controllers;

import com.example.cricketApplication.models.ERole;
import com.example.cricketApplication.models.Official;
import com.example.cricketApplication.models.User;
import com.example.cricketApplication.payload.request.SignupRequest;
import com.example.cricketApplication.payload.response.MessageResponse;
import com.example.cricketApplication.payload.response.OfficialResponse;
import com.example.cricketApplication.repository.RoleRepository;
import com.example.cricketApplication.repository.UserRepository;
import com.example.cricketApplication.security.services.OfficialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/officials")
public class OfficialController {

    @Autowired
    private OfficialService officialService;



    // Get official by id with OfficialResponse
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH', 'ROLE_PLAYER', 'ROLE_OFFICIAL')")
    public ResponseEntity<OfficialResponse> getOfficialById(@PathVariable Long id) {
        OfficialResponse officialResponse = officialService.getOfficialResponseById(id);
        return ResponseEntity.ok(officialResponse);
    }

    // Get all officials with OfficialResponse
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH', 'ROLE_PLAYER', 'ROLE_OFFICIAL')")
    public ResponseEntity<List<OfficialResponse>> getAllOfficials() {
        List<OfficialResponse> officials = officialService.getAllOfficialResponses();
        return ResponseEntity.ok(officials);
    }

    // Update official by id with OfficialResponse
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OfficialResponse> updateOfficial(
            @PathVariable Long id,
            @RequestParam("officialData") String officialData, // Official details as JSON
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            // Parse the JSON data into the Official object
            ObjectMapper objectMapper = new ObjectMapper();
            Official officialDetails = objectMapper.readValue(officialData, Official.class);

            // Call the service to update the official with the image (if provided)
            OfficialResponse updatedOfficial = officialService.updateOfficial(id, officialDetails, imageFile);

            return ResponseEntity.ok(updatedOfficial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OfficialResponse("Error: " + e.getMessage()));
        }
    }



    // Delete official
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteOfficial(@PathVariable Long id) {
        officialService.deleteOfficial(id);
        return ResponseEntity.noContent().build();
    }
}






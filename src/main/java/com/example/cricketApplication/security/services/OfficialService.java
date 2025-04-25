package com.example.cricketApplication.security.services;

import com.example.cricketApplication.exceptions.OfficialNotFoundException;
import com.example.cricketApplication.exceptions.PlayerAlreadyExistsException;
import com.example.cricketApplication.models.Official;
import com.example.cricketApplication.models.User;
import com.example.cricketApplication.payload.response.OfficialResponse;
import com.example.cricketApplication.repository.OfficialRepository;
import com.example.cricketApplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficialService {

    @Autowired
    private OfficialRepository officialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private static final String IMAGE_DIRECTORY = "D:\\upload\\";

    // Get official by id method with OfficialResponse
    public OfficialResponse getOfficialResponseById(Long id) {
        Official official = officialRepository.findById(id)
                .orElseThrow(() -> new OfficialNotFoundException("Official not found with ID: " + id));
        return refactorResponse(official);
    }

    // Get all officials method with OfficialResponse
    public List<OfficialResponse> getAllOfficialResponses() {
        List<Official> officials = officialRepository.findAll();
        return officials.stream()
                .map(this::refactorResponse)  // Convert each Official to OfficialResponse
                .collect(Collectors.toList());
    }

    // Update official method with OfficialResponse
    @Transactional
    public OfficialResponse updateOfficial(Long id, Official officialDetails, MultipartFile imageFile) {
        Official official = officialRepository.findById(id)
                .orElseThrow(() -> new OfficialNotFoundException("Official not found with ID: " + id));

        User user = official.getUser();

        // Check if the email is already taken by another user
        if (userRepository.existsByEmail(officialDetails.getUser().getEmail())
                && !user.getEmail().equals(officialDetails.getUser().getEmail())) {
            throw new PlayerAlreadyExistsException("Official with email " + officialDetails.getUser().getEmail() + " already exists.");
        }

        // Check if the username is already taken by another user
        if (userRepository.existsByUsername(officialDetails.getUser().getUsername())
                && !user.getUsername().equals(officialDetails.getUser().getUsername())) {
            throw new PlayerAlreadyExistsException("Official with username " + officialDetails.getUser().getUsername() + " already exists.");
        }

        // Update user details if present
        if (officialDetails.getUser() != null) {
            if (officialDetails.getUser().getUsername() != null && !officialDetails.getUser().getUsername().isEmpty()) {
                user.setUsername(officialDetails.getUser().getUsername());
            }
            if (officialDetails.getUser().getEmail() != null && !officialDetails.getUser().getEmail().isEmpty()) {
                user.setEmail(officialDetails.getUser().getEmail());
            }
            if (officialDetails.getUser().getPassword() != null && !officialDetails.getUser().getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(officialDetails.getUser().getPassword());
                user.setPassword(encodedPassword);
            }
            userRepository.save(user);
        }

        // Update official details
        official.setName(officialDetails.getName());
        official.setContactNo(officialDetails.getContactNo());
        official.setPosition(officialDetails.getPosition());
        official.setUpdatedOn(officialDetails.getUpdatedOn());
        official.setUpdatedBy(officialDetails.getUpdatedBy());

        // Save image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = officialDetails.getUser().getUsername() + ".jpg"; // Use official's username or other identifier
                String imagePath = IMAGE_DIRECTORY + fileName;

                // Save the image file locally
                Files.write(Paths.get(imagePath), imageFile.getBytes());

                // Update official image path
                official.setImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error saving image file: " + e.getMessage(), e);
            }
        }

        // Save the updated official
        Official updatedOfficial = officialRepository.save(official);

        return RefactorResponse(updatedOfficial);
    }


    // Delete official method
    public void deleteOfficial(Long id) {
        Official official = officialRepository.findById(id)
                .orElseThrow(() -> new OfficialNotFoundException("Official not found with ID: " + id));
        officialRepository.delete(official);
    }

    private OfficialResponse RefactorResponse(Official official) {
        OfficialResponse officialResponse = new OfficialResponse();
        officialResponse.setOfficialId(official.getId());
        officialResponse.setName(official.getName());
        officialResponse.setContactNo(official.getContactNo());
        officialResponse.setPosition(official.getPosition());

        if (official.getUser() != null) {
            officialResponse.setUsername(official.getUser().getUsername());
            officialResponse.setEmail(official.getUser().getEmail());
            officialResponse.setPassword(official.getUser().getPassword());
        } else {
            officialResponse.setUsername(null);
            officialResponse.setEmail(null);
            officialResponse.setPassword(null);
        }

        // Set image URL properly
        if (official.getImage() != null) {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(official.getImage())
                    .toUriString();
            officialResponse.setImage(imageUrl);
        } else {
            officialResponse.setImage(null);
        }

        officialResponse.setCreatedBy(official.getCreatedBy());
        officialResponse.setUpdatedBy(official.getUpdatedBy());
        officialResponse.setCreatedOn(official.getCreatedOn());
        officialResponse.setUpdatedOn(official.getUpdatedOn());

        return officialResponse;
    }

    // Method to convert Official to OfficialResponse
    private OfficialResponse refactorResponse(Official official) {
        OfficialResponse officialResponse = new OfficialResponse();
        officialResponse.setOfficialId(official.getId());
        officialResponse.setName(official.getName());
        officialResponse.setContactNo(official.getContactNo());
        officialResponse.setPosition(official.getPosition());

        if (official.getUser() != null) {
            officialResponse.setPassword(official.getUser().getPassword());
            officialResponse.setEmail(official.getUser().getEmail());
            officialResponse.setUsername(official.getUser().getUsername());
        } else {
            officialResponse.setPassword(null);
            officialResponse.setEmail(null);
            officialResponse.setUsername(null);
        }

        // Set image URL properly
        if (official.getImage() != null) {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(official.getImage())
                    .toUriString();
            officialResponse.setImage(imageUrl);
        } else {
            officialResponse.setImage(null);
        }

        officialResponse.setCreatedBy(official.getCreatedBy());
        officialResponse.setUpdatedBy(official.getUpdatedBy());
        officialResponse.setCreatedOn(official.getCreatedOn());
        officialResponse.setUpdatedOn(official.getUpdatedOn());

        return officialResponse;
    }

}



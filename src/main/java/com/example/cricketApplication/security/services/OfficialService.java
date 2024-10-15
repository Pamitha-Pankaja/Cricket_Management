package com.example.cricketApplication.security.services;

import com.example.cricketApplication.models.Official;
import com.example.cricketApplication.models.User;
import com.example.cricketApplication.repository.OfficialRepository;
import com.example.cricketApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficialService {

    @Autowired
    private OfficialRepository officialRepository;

    // Get Official by ID
    public Official getOfficialById(Long id) {
        return officialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Official not found with id: " + id));
    }

    // Update Official
    public Official updateOfficial(Long id, Official updatedOfficial) {
        Official official = officialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Official not found with id: " + id));

        official.setName(updatedOfficial.getName());
        official.setContactNo(updatedOfficial.getContactNo());
        official.setPosition(updatedOfficial.getPosition());

        return officialRepository.save(official);
    }

    // Delete Official
    public void deleteOfficial(Long id) {
        Official official = officialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Official not found with id: " + id));
        officialRepository.delete(official);
    }

    // Get All Officials
    public List<Official> getAllOfficials() {
        return officialRepository.findAll();
    }
}



package com.example.cricketApplication.repository;

import com.example.cricketApplication.models.Player;
import com.example.cricketApplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByEmail(String email);
    // You can define custom query methods here if needed
    Optional<Player> findByUser(User user);
}


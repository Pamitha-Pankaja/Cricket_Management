package com.example.cricketApplication.repository;

import com.example.cricketApplication.models.Coach;
import com.example.cricketApplication.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}

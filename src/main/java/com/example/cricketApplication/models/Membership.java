package com.example.cricketApplication.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "memberships")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", referencedColumnName = "playerId", nullable = false)
    private Player player;

//    @Transient  // This field is not stored in the database, it's calculated on the fly.
//    private boolean isActive;
//
//    public boolean getIsActive() {
//        Date now = new Date();
//        return now.after(startDate) && now.before(endDate);
//    }

    // Getters and setters
}


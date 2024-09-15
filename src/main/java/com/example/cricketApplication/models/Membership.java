package com.example.cricketApplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "memberships")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column()
//    private boolean isMember;

    private String isMemberHolder;


    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "player_id", referencedColumnName = "playerId", nullable = true)
//    private Player player;


//    @Transient
//    // This field is not stored in the database, it's calculated on the fly.
//    private boolean isActive;
//
//    public boolean getIsActive() {
//        Date now = new Date();
//        return now.after(startDate) && now.before(endDate);
//    }

    // Getters and setters
}


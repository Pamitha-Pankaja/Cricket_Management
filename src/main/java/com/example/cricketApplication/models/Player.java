package com.example.cricketApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @NotBlank
    private String name;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Email
    private String email;

    private String contactNo;

    private String battingStyle;
    private String bowlingStyle;

    private String status;

    private String image;

    private String playerRole;


    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PlayerStats> playerStats = new HashSet<>();

    @ManyToMany(mappedBy = "players")
    private Set<PractiseSession> practiseSessions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "players")
    private Set<Team> teams = new HashSet<>();

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Membership membership;

    // Getters and setters
}

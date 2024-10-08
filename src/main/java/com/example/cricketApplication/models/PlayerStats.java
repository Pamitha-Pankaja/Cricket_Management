package com.example.cricketApplication.models;

import jakarta.persistence.*;
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@Entity
@Table(name = "player_stats")
public class PlayerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inning; // "1st Inning", "2nd Inning", etc.

    private int runs;
    private int wickets;
    private int fours;
    private int sixers;
    private int fifties;
    private int centuries;
    private int balls;
    private int overs;
    private int runsConceded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    // Getters and setters
}

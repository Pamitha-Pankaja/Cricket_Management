package com.example.cricketApplication.models;

import jakarta.persistence.*;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@Entity
@Table(name = "match_summary")
public class MatchSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inning; // "1st", "2nd", etc.

    private int runs;
    private int wickets;
    private int overs;
    private int oppositionRuns;
    private int oppositionWickets;
    private int oppositionOvers;
    private String result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;



    // Getters and setters
}

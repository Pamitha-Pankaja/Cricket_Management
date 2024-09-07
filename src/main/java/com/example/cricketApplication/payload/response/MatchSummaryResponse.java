package com.example.cricketApplication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchSummaryResponse {
    private Long id;
    private String inning;
    private int runs;
    private int wickets;
    private int overs;
    private int oppositionRuns;
    private int oppositionWickets;
    private int oppositionOvers;
    private String result;
    private Long matchId; // Match ID for reference
}

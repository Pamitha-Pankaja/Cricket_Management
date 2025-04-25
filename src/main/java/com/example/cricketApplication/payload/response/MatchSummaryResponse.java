package com.example.cricketApplication.payload.response;

import com.example.cricketApplication.models.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchSummaryResponse {
    private Long id;
    private String inning;
    private Integer runs;
    private Integer wickets;
    private BigDecimal overs;
    private Integer oppositionRuns;
    private Integer oppositionWickets;
    private BigDecimal oppositionOvers;
    private String result;
    private Long matchId;
    private String venue;
    private String opposition;
    private Date date;
    private String type;
    private String under;
    private Long teamId;
    private Integer teamYear;
    private String logo;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
}


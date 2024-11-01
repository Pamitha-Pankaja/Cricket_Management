package com.example.cricketApplication.payload.response;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class TeamResponse {

    private Long teamId;

    private String under; // "Under 13", "Under 15", etc.
    private int year;
    private String captain;
    private String ViceCaptain;
}

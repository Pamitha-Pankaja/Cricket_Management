package com.example.cricketApplication.payload.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class PlayerResponse {

    private Long playerId;
    private String name;
    private Date dateOfBirth;
    private String email;
    private String contactNo;
    private String battingStyle;
    private String bowlingStyle;
    private String status;
    private String image;
    private String playerRole;
}

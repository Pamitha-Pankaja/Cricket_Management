package com.example.cricketApplication.payload.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class PracticeSessionResponse {
    private Long pracId;

    private String venue;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String starTime;
    private String endTime;
    private String pracType;
    private String teamUnder;
}
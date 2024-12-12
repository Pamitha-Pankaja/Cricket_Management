package com.example.cricketApplication.payload.response;
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class AdminResponse {

    private Long adminId;
    private String name;
    private String contactNo;
    private String email;
    private String password;
    private String username;
}

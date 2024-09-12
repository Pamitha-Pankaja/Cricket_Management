package com.example.cricketApplication.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  //@NotBlank
  private String name;

  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

  private String contactNo;

  private String battingStyle;
  private String bowlingStyle;

  private String status;

  private String image;

  private String playerRole;


//  public User(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 40) String password, String name, String contactNo, String battingStyle, String bowlingStyle, String status, String image, String playerRole, String encode) {
//  }

  public User(String username, String email, String password, String name, String contactNo, String battingStyle, String bowlingStyle, String status, String image,String playerRole) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.contactNo = contactNo;
    this.battingStyle = battingStyle;
    this.bowlingStyle = bowlingStyle;
    this.status = status;
    this.image = image;
    this.playerRole = playerRole;

  }

//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  public String getUsername() {
//    return username;
//  }
//
//  public void setUsername(String username) {
//    this.username = username;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }
//
//  public Set<Role> getRoles() {
//    return roles;
//  }
//
//  public void setRoles(Set<Role> roles) {
//    this.roles = roles;
//  }
}

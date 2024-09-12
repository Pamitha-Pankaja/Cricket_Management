package com.example.cricketApplication.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.cricketApplication.models.*;
import com.example.cricketApplication.repository.CoachRepository;
import com.example.cricketApplication.repository.PlayerRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cricketApplication.payload.request.LoginRequest;
import com.example.cricketApplication.payload.request.SignupRequest;
import com.example.cricketApplication.payload.response.JwtResponse;
import com.example.cricketApplication.payload.response.MessageResponse;
import com.example.cricketApplication.repository.RoleRepository;
import com.example.cricketApplication.repository.UserRepository;
import com.example.cricketApplication.security.jwt.JwtUtils;
import com.example.cricketApplication.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CoachRepository coachRepository;

    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getContactNo(),
                signUpRequest.getBattingStyle(),
                signUpRequest.getBowlingStyle(),
                signUpRequest.getStatus(),
                signUpRequest.getImage(),
                signUpRequest.getPlayerRole()
                );
        //System.out.println(user.getName());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newRole = new Role(ERole.ROLE_USER);
                        roleRepository.save(newRole);
                        return newRole;
                    });
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseGet(() -> {
                                    Role newRole = new Role(ERole.ROLE_ADMIN);
                                    roleRepository.save(newRole);
                                    return newRole;
                                });
                        roles.add(adminRole);
                        break;

                    case "ROLE_COACH":
                        Role coachRole = roleRepository.findByName(ERole.ROLE_COACH)
                                .orElseGet(() -> {
                                    Role newRole = new Role(ERole.ROLE_COACH);
                                    roleRepository.save(newRole);
                                    return newRole;
                                });
                        roles.add(coachRole);
                        break;

                    case "ROLE_PLAYER":
                        Role playerRole = roleRepository.findByName(ERole.ROLE_PLAYER)
                                .orElseGet(() -> {
                                    Role newRole = new Role(ERole.ROLE_PLAYER);
                                    roleRepository.save(newRole);
                                    return newRole;
                                });
                        roles.add(playerRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseGet(() -> {
                                    Role newRole = new Role(ERole.ROLE_USER);
                                    roleRepository.save(newRole);
                                    return newRole;
                                });
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user); // Save the User entity first

        // If the user has the coach role, save them to the Coach table
        if (roles.stream().anyMatch(role -> role.getName().equals(ERole.ROLE_COACH))) {
            Coach coach = new Coach();
            coach.setEmail(savedUser.getEmail());
            coach.setName(savedUser.getUsername());
            coach.setRole(roleRepository.findByName(ERole.ROLE_COACH)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
            coach.setUser(savedUser);
            coachRepository.save(coach);
        }

        // If the user has the player role, save them to the Player table
        if (roles.stream().anyMatch(role -> role.getName().equals(ERole.ROLE_PLAYER))) {
            Player player = new Player();
            player.setEmail(savedUser.getEmail());
            player.setName(savedUser.getName());
            player.setBattingStyle(savedUser.getBattingStyle());
            player.setBowlingStyle(savedUser.getBowlingStyle());
            player.setContactNo(savedUser.getContactNo());
            player.setImage(savedUser.getImage());
            player.setStatus(savedUser.getStatus());
            player.setPlayerRole(savedUser.getPlayerRole());
            player.setRole(roleRepository.findByName(ERole.ROLE_PLAYER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
            player.setUser(savedUser);
            playerRepository.save(player);
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}





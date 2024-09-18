package com.example.cricketApplication.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.cricketApplication.models.*;
import com.example.cricketApplication.repository.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.cricketApplication.payload.request.LoginRequest;
import com.example.cricketApplication.payload.request.SignupRequest;
import com.example.cricketApplication.payload.response.JwtResponse;
import com.example.cricketApplication.payload.response.MessageResponse;
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

    @Autowired
    MembershipRepository membershipRepository;


//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                roles));
//    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Fetch the User entity from the database
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        // Check if the user has the ROLE_PLAYER
        if (roles.contains("ROLE_PLAYER")) {
            Player player = playerRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Error: Player not found."));

            // Check if the player has a membership and if it is active
            Membership membership = player.getMembership();
            if (membership == null || !membership.getIsActive()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Player's membership is expired or not available."));
            }

        }

        // Generate JWT token if all checks passed
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }




//    @PostMapping("/signupPlayer")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        Player user = new Player(
//                signUpRequest.getName(),
//                signUpRequest.getContactNo(),
//                signUpRequest.getBattingStyle(),
//                signUpRequest.getBowlingStyle(),
//                signUpRequest.getStatus(),
//                signUpRequest.getImage(),
//                signUpRequest.getPlayerRole()
//
//                );
//        //System.out.println(user.getName());
//
//        Set<String> strRoles = signUpRequest.getRoles();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseGet(() -> {
//                        Role newRole = new Role(ERole.ROLE_USER);
//                        roleRepository.save(newRole);
//                        return newRole;
//                    });
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "ROLE_ADMIN":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseGet(() -> {
//                                    Role newRole = new Role(ERole.ROLE_ADMIN);
//                                    roleRepository.save(newRole);
//                                    return newRole;
//                                });
//                        roles.add(adminRole);
//                        break;
//
//                    case "ROLE_COACH":
//                        Role coachRole = roleRepository.findByName(ERole.ROLE_COACH)
//                                .orElseGet(() -> {
//                                    Role newRole = new Role(ERole.ROLE_COACH);
//                                    roleRepository.save(newRole);
//                                    return newRole;
//                                });
//                        roles.add(coachRole);
//                        break;
//
//                    case "ROLE_PLAYER":
//                        Role playerRole = roleRepository.findByName(ERole.ROLE_PLAYER)
//                                .orElseGet(() -> {
//                                    Role newRole = new Role(ERole.ROLE_PLAYER);
//                                    roleRepository.save(newRole);
//                                    return newRole;
//                                });
//                        roles.add(playerRole);
//                        break;
//
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseGet(() -> {
//                                    Role newRole = new Role(ERole.ROLE_USER);
//                                    roleRepository.save(newRole);
//                                    return newRole;
//                                });
//                        roles.add(userRole);
//                }
//            });
//        }
//        User newUser = new User();
//        newUser.setRoles(roles);
//        newUser.setUsername(signUpRequest.getUsername());
//        newUser.setEmail(signUpRequest.getEmail());
//        newUser.setPassword(
//                encoder.encode(signUpRequest.getPassword()));
//        user.setUser(newUser);
//        user.setMembership(signUpRequest.getMembership());
//        membershipRepository.save(signUpRequest.getMembership());
////        user.setMembership(signUpRequest.getMembership());
//        userRepository.save(newUser);
//        playerRepository.save(user); // Save the User entity first
//
//        // If the user has the coach role, save them to the Coach table
////        if (roles.stream().anyMatch(role -> role.getName().equals(ERole.ROLE_COACH))) {
////            Coach coach = new Coach();
////            coach.setEmail(savedUser.getEmail());
////            coach.setName(savedUser.getUsername());
////            coach.setRole(roleRepository.findByName(ERole.ROLE_COACH)
////                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
////            coach.setUser(savedUser);
////            coachRepository.save(coach);
////        }
//
//        // If the user has the player role, save them to the Player table
////        if (roles.stream().anyMatch(role -> role.getName().equals(ERole.ROLE_PLAYER))) {
////            Player player = new Player();
////            player.setEmail(savedUser.getEmail());
////            player.setName(savedUser.getName());
////            player.setBattingStyle(savedUser.getBattingStyle());
////            player.setBowlingStyle(savedUser.getBowlingStyle());
////            player.setContactNo(savedUser.getContactNo());
////            player.setImage(savedUser.getImage());
////            player.setStatus(savedUser.getStatus());
////            player.setPlayerRole(savedUser.getPlayerRole());
////            player.setRole(roleRepository.findByName(ERole.ROLE_PLAYER)
////                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
////            player.setUser(savedUser);
////            playerRepository.save(player);
////        }
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }



//    @PostMapping("/signupPlayer")
//    public ResponseEntity<?> registerPlayer(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        Player player = new Player(
//                signUpRequest.getName(),
//                signUpRequest.getContactNo(),
//                signUpRequest.getBattingStyle(),
//                signUpRequest.getBowlingStyle(),
//                signUpRequest.getStatus(),
//                signUpRequest.getImage(),
//                signUpRequest.getPlayerRole(),
//                signUpRequest.getMembership()
//        );
//
//        // Create and set user entity
//        User newUser = new User();
//        newUser.setUsername(signUpRequest.getUsername());
//        newUser.setEmail(signUpRequest.getEmail());
//        newUser.setPassword(encoder.encode(signUpRequest.getPassword()));
//
//        // Set default role for player
//        Role playerRole = roleRepository.findByName(ERole.ROLE_PLAYER)
//                .orElseGet(() -> {
//                    Role newRole = new Role(ERole.ROLE_PLAYER);
//                    roleRepository.save(newRole);
//                    return newRole;
//                });
//        Set<Role> roles = new HashSet<>();
//        roles.add(playerRole);
//        newUser.setRoles(roles);
//
//        // Link the player to the user entity
//        player.setUser(newUser);
//
//        // Save the user and player entities
//        userRepository.save(newUser);
//        playerRepository.save(player);
//
//        return ResponseEntity.ok(new MessageResponse("Player registered successfully!"));
//    }

    @PostMapping("/signupPlayer")
    public ResponseEntity<?> registerPlayer(@Valid @RequestBody SignupRequest signUpRequest) {
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

        // Save membership entity first
        Membership membership = signUpRequest.getMembership();
        if (membership != null) {
            membership = membershipRepository.save(membership); // Save membership
        }

        // Create new player's account
        Player player = new Player(
                signUpRequest.getName(),
                signUpRequest.getContactNo(),
                signUpRequest.getBattingStyle(),
                signUpRequest.getBowlingStyle(),
                signUpRequest.getStatus(),
                signUpRequest.getImage(),
                signUpRequest.getPlayerRole(),
                membership // Set the saved membership
        );

        // Create and set user entity
        User newUser = new User();
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(encoder.encode(signUpRequest.getPassword()));

        // Set default role for player
        Role playerRole = roleRepository.findByName(ERole.ROLE_PLAYER)
                .orElseGet(() -> {
                    Role newRole = new Role(ERole.ROLE_PLAYER);
                    roleRepository.save(newRole);
                    return newRole;
                });
        Set<Role> roles = new HashSet<>();
        roles.add(playerRole);
        newUser.setRoles(roles);

        // Link the player to the user entity
        player.setUser(newUser);

        // Save the user and player entities
        userRepository.save(newUser);
        playerRepository.save(player);

        return ResponseEntity.ok(new MessageResponse("Player registered successfully!"));
    }




    @PostMapping("/signupCoach")
    public ResponseEntity<?> registerCoach(@Valid @RequestBody SignupRequest signUpRequest) {
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
        Coach coach = new Coach();
        coach.setName(signUpRequest.getName());
        coach.setContactNo(signUpRequest.getContactNo());
        coach.setEmail(signUpRequest.getEmail());
        coach.setImage(signUpRequest.getImage());
        coach.setDescription("Coach Description"); // Set other coach-specific fields as needed

        // Create and set user entity
        User newUser = new User();
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(encoder.encode(signUpRequest.getPassword()));

        // Set default role for coach
        Role coachRole = roleRepository.findByName(ERole.ROLE_COACH)
                .orElseGet(() -> {
                    Role newRole = new Role(ERole.ROLE_COACH);
                    roleRepository.save(newRole);
                    return newRole;
                });
        Set<Role> roles = new HashSet<>();
        roles.add(coachRole);
        newUser.setRoles(roles);

        // Link the coach to the user entity
        coach.setUser(newUser);

        // Save the user and coach entities
        userRepository.save(newUser);
        coachRepository.save(coach);

        return ResponseEntity.ok(new MessageResponse("Coach registered successfully!"));
    }





}





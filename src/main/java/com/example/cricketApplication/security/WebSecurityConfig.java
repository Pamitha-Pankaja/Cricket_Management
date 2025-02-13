package com.example.cricketApplication.security;

import com.example.cricketApplication.security.jwt.AuthEntryPointJwt;
import com.example.cricketApplication.security.jwt.AuthTokenFilter;
import com.example.cricketApplication.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
// (securedEnabled = true,
// jsr250Enabled = true,
// prePostEnabled = true) // by default
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http.csrf(csrf -> csrf.disable())
//        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .authorizeHttpRequests(auth ->
//          auth.requestMatchers("/**","/api/auth/**","/api/matches/**","/api/matchSummary/**","/api/playerStats/**","/api/coaches/**","/api/practiseSessions/**","/api/teams/**","/api/matchSummary","/api/admin/players/**","/api/privileges/**","/api/news/**","/api/videos/**","/api/officials/**","/images/**","/api/admin/**").permitAll()
////                  .requestMatchers(HttpMethod.POST, "/api/auth/signupPlayer", "/api/auth/signupCoach", "/api/auth/signupOfficial").hasRole("ADMIN")
//        );
//
//    http.authenticationProvider(authenticationProvider());
//
//    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//  }



  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // Public endpoints (if any adjust as needed)
                    .requestMatchers(
                            "/api/videos/**",
                            "/images/**",
                            "/api/auth/signin",
                            "/api/auth/checkAvailability",
                            "/api/auth/checkUsernameAvailability",
                            "/api/auth/checkEmailAvailability"
                    ).permitAll()

                            // Auth endpoints requiring admin role
                            .requestMatchers(HttpMethod.POST, "/api/auth/signupPlayer").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/auth/signupCoach").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/auth/signup").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/auth/signupOfficial").hasRole("ADMIN")

                            // Auth logout endpoint (usually authenticated users only)
                            .requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()

                    // Coach endpoints
                    .requestMatchers(HttpMethod.POST, "/api/coaches/add").authenticated()
                    .requestMatchers(HttpMethod.GET,
                            "/api/coaches/all",
                            "/api/coaches/{coachId}",
                            "/api/coaches/user/{userId}",
                            "/api/coaches/role/{roleId}")
                    .hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.DELETE, "/api/coaches/{coachId}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/coaches/{coachId}").hasRole("ADMIN")

                    // Match endpoints
                    .requestMatchers(HttpMethod.POST, "/api/matches/add").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,
                            "/api/matches/{id}",
                            "/api/matches/{matchId}/hasRequiredSummaries",
                            "/api/matches/all")
                    .hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.DELETE, "/api/matches/delete/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/matches/update/{id}").hasRole("ADMIN")

                    // MatchSummary endpoints
                    .requestMatchers(HttpMethod.POST, "/api/matchSummary/add").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,
                            "/api/matchSummary/{id}",
                            "/api/matchSummary/all",
                            "/api/matchSummary/match/{matchId}")
                    .hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.DELETE, "/api/matchSummary/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/matchSummary/update/{id}").hasRole("ADMIN")

                    // News endpoints
//                    .requestMatchers(HttpMethod.GET, "/api/news", "/api/news/{id}")
//                    .hasAnyRole("ADMIN", "COACH", "PLAYER", "OFFICIAL")
                    .requestMatchers(HttpMethod.GET, "/api/news", "/api/news/{id}").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/news/create").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/news/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/news/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/news/deleteImages/{newsId}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/news/deleteImage/{imageId}").hasRole("ADMIN")

                    // Officials endpoints
                    .requestMatchers(HttpMethod.GET,
                            "/api/officials/{id}",
                            "/api/officials/all")
                    .hasAnyRole("ADMIN", "COACH", "PLAYER", "OFFICIAL")
                    .requestMatchers(HttpMethod.PUT, "/api/officials/update/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/officials/delete/{id}").hasRole("ADMIN")

                    // Player endpoints
                    .requestMatchers(HttpMethod.POST, "/api/admin/players/add").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/admin/players/{id}")
                    .hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.GET, "/api/admin/players/all")
                    .hasAnyRole("USER","ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.PUT, "/api/admin/players/update/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/admin/players/delete/{id}").hasRole("ADMIN")

                    // PlayerStats endpoints
                    .requestMatchers(HttpMethod.POST, "/api/playerStats/add").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/playerStats/addMultiple").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/playerStats/update/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/playerStats/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,
                            "/api/playerStats/{id}",
                            "/api/playerStats/all",
                            "/api/playerStats/player/{playerId}",
                            "/api/playerStats/match/{matchId}",
                            "/api/playerStats/match",
                            "/api/playerStats/type",
                            "/api/playerStats/summery",
                            "/api/playerStats/match/player-stats",
                            "/api/playerStats/all-stats/{playerId}")
                    .hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")

                    // PractiseSession endpoints
                    .requestMatchers(HttpMethod.POST, "/api/practiseSessions/add").hasAnyRole("ADMIN","COACH")
                    .requestMatchers(HttpMethod.GET, "/api/practiseSessions/all").hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.GET, "/api/practiseSessions/{pracId}").hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.DELETE, "/api/practiseSessions/{pracId}").hasAnyRole("ADMIN","COACH")
                    .requestMatchers(HttpMethod.GET, "/api/practiseSessions/coach/{coachId}").hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.PUT, "/api/practiseSessions/update/{pracId}").hasAnyRole("ADMIN","COACH")
                    .requestMatchers(HttpMethod.GET, "/api/practiseSessions/player/{playerId}").hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")

                    // Teams endpoints
                    .requestMatchers(HttpMethod.POST, "/api/teams/add").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/teams/{id}").hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.GET, "/api/teams/all").hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")
                    .requestMatchers(HttpMethod.DELETE, "/api/teams/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/teams/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/teams/{id}/players").hasAnyRole("ADMIN","COACH","PLAYER","OFFICIAL")

                    // Any other request requires authentication
                    //.anyRequest().authenticated()
            );

    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }












}

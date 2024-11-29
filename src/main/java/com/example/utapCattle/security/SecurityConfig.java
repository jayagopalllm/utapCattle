package com.example.utapCattle.security;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // Disable CSRF as you're building a stateless API (if needed)
//        http.csrf(AbstractHttpConfigurer::disable)
//                // Allow all requests (no authentication required)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll() // Permit all requests
//                        .anyRequest().permitAll())  // Ensure that any other requests are also permitted
//                // Disable session management (stateless API)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}

package com.vinayakachaviti.config;

import com.vinayakachaviti.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // CORS_ALLOWED_ORIGINS: comma-separated list of allowed frontend origins (e.g. the
    // Netlify production URL). Defaults to local Vite dev server origins.
    @Value("${CORS_ALLOWED_ORIGINS:http://localhost:5173,http://127.0.0.1:5173}")
    private String corsAllowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/api/festivals/**").permitAll()
                // Public read-only festival endpoints remain open; mutations now require ADMIN JWT.
                .requestMatchers(HttpMethod.POST, "/api/festivals/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/festivals/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/festivals/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/festivals/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                // Public read-only event endpoints remain open; mutations require ADMIN JWT.
                .requestMatchers(HttpMethod.POST, "/api/events/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/events/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/events/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/events/**").hasRole("ADMIN")
                // Admin-only listing sub-paths must be matched before the general public GET rules below.
                .requestMatchers(HttpMethod.GET, "/api/families/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/announcements/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/gallery/albums/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/videos/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/puja/schedules/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/puja/content/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/donations/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/families/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/families/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/families/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/families/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/announcements/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/announcements/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/announcements/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/announcements/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/announcements/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/gallery/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/gallery/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/gallery/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/gallery/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/gallery/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/videos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/videos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/videos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/videos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasRole("ADMIN")
                // US-PUJA: Public read-only puja schedule/spiritual content; mutations require ADMIN JWT.
                .requestMatchers(HttpMethod.GET, "/api/puja/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/puja/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/puja/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/puja/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/puja/**").hasRole("ADMIN")
                // US-DONATIONS: Public can create a donation and view stats; all other mutations/listing are ADMIN only.
                .requestMatchers(HttpMethod.POST, "/api/donations").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/donations/stats").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/api/donations/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/donations/**").hasRole("ADMIN")
                // US-CONTRIBUTIONS: Public can list/search contributions; mutations are ADMIN only.
                .requestMatchers(HttpMethod.GET, "/api/contributions/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/contributions/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contributions/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/contributions/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/contributions/**").hasRole("ADMIN")
                .requestMatchers("/uploads/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                // US-AUDIT: Audit trail is admin-only.
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = Arrays.stream(corsAllowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .toList();
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}

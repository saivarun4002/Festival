package com.vinayakachaviti.auth.service;

import com.vinayakachaviti.auth.dto.LoginRequest;
import com.vinayakachaviti.auth.dto.LoginResponse;
import com.vinayakachaviti.auth.entity.AdminUser;
import com.vinayakachaviti.auth.repository.AdminUserRepository;
import com.vinayakachaviti.exception.BadRequestException;
import com.vinayakachaviti.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        AdminUser user = adminUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!user.isEnabled()) {
            throw new BadRequestException("This admin account is disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .username(user.getUsername())
                .role(user.getRole())
                .expiresInSeconds(jwtService.getExpirationSeconds())
                .build();
    }
}
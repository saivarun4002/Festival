package com.vinayakachaviti.auth.config;

import com.vinayakachaviti.auth.entity.AdminUser;
import com.vinayakachaviti.auth.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * US-AUTH: Seeds a single default admin account (ADMIN_CREATED_SEED) on startup
 * if no admin users exist yet. Credentials are read from env vars so nothing
 * sensitive is hardcoded; falls back to a dev-only default for local use.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserSeeder implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminUserRepository.count() > 0) {
            return;
        }

        String username = System.getenv().getOrDefault("DEFAULT_ADMIN_USERNAME", "admin");
        String rawPassword = System.getenv().getOrDefault("DEFAULT_ADMIN_PASSWORD", "ChangeMe@123");

        AdminUser admin = AdminUser.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .role("ADMIN")
                .enabled(true)
                .build();

        adminUserRepository.save(admin);
        log.info("Seeded default admin user '{}'. Change this password immediately in production.", username);
    }
}

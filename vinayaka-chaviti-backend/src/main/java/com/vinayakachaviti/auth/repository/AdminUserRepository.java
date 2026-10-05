package com.vinayakachaviti.auth.repository;

import com.vinayakachaviti.auth.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    //to find an admin user by their username, used for authentication
    Optional<AdminUser> findByUsername(String username);

    //to check if an admin user with a given username already exists, used for validation during registration
    boolean existsByUsername(String username);
}

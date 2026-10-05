package com.vinayakachaviti.auth.service;

import com.vinayakachaviti.auth.dto.LoginRequest;
import com.vinayakachaviti.auth.dto.LoginResponse;

public interface AuthService {
    //to authenticate a user and return a JWT token along with user details
    LoginResponse login(LoginRequest request);
}
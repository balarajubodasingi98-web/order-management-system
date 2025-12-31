package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;

public interface UserService {
	
	User register(User user);
	
	AuthResponse login(AuthRequest request);

}

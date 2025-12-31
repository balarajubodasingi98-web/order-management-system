package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController{
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private JwtUtil jwtUtil;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	private final UserService userService;

	
	public AuthController(UserService userService) {

		this.userService = userService;
	}

	@PostMapping("/register")
	public String register(@RequestBody User user) {
//	      user.setPassword(passwordEncoder.encode(user.getPassword()));
//	      user.setRole("USER");
//	      userRepository.save(user);
		  userService.register(user);
	      return "User Registered Sucessfully";
	}
	
	@PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

//        User user = userRepository.findByEmail(request.email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(request.password, user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        return new AuthResponse(jwtUtil.generateToken(user.getEmail()));
		return userService.login(request);
    }
	
	
}
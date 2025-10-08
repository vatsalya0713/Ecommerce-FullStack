package com.genie.Ecomm.controller;

import com.genie.Ecomm.dto.LoginRequest;
import com.genie.Ecomm.dto.LoginResponse;
import com.genie.Ecomm.model.User;
import com.genie.Ecomm.util.JwtUtil;
import com.genie.Ecomm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwtToken = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }


    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> userOptional = userService.findByUsername(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            Map<String, String> profile = new HashMap<>();
            profile.put("name", user.getUsername());
            profile.put("email", user.getEmail());

            return ResponseEntity.ok(profile);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

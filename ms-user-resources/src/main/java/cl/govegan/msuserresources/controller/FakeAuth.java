package cl.govegan.msuserresources.controller;

import cl.govegan.msuserresources.models.User;
import cl.govegan.msuserresources.repositories.UserRepository;
import cl.govegan.msuserresources.services.JwtService;
import cl.govegan.msuserresources.services.UserConverter;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class FakeAuth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserConverter userConverter;
    private final JwtService jwtService;

    @PostMapping("/loginUser")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = new AuthResponse();

        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String accessToken = jwtService.generateToken(user);
                String refreshToken = jwtService.generateRefreshToken();
                authResponse.setToken(accessToken);
                authResponse.setRefreshToken(refreshToken);
                authResponse.setMessage("User logged in successfully");
                return ResponseEntity.ok(authResponse);
            } else {
                authResponse.setMessage("Invalid credentials");
                return ResponseEntity.badRequest().body(authResponse);
            }
        } else {
            authResponse.setMessage("User not found");
            return ResponseEntity.badRequest().body(authResponse);
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = new AuthResponse();

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            authResponse.setMessage("Username is already taken");
            return ResponseEntity.badRequest().body(authResponse);
        }

        User user = userConverter.toUser(registerRequest, passwordEncoder);
        userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken();

        authResponse.setToken(accessToken);
        authResponse.setRefreshToken(refreshToken);
        authResponse.setMessage("User registered successfully");
        return ResponseEntity.ok(authResponse);
    }

    @Data
    public static class AuthResponse {
        private String message;
        private String token;
        private String refreshToken;
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private String name;
        private String profilePicture;
        private int age;
        private String gender;
        private double weight;
        private double height;
        private String city;
        private String country;
        private ArrayList<String> allergies;
        private ArrayList<String> favoriteFoods;
        private ArrayList<String> unwantedFoods;
        private ArrayList<String> favoriteRecipes;
        private int caloriesPerDay;
        private double waterPerDay;
        private String title;
    }

}

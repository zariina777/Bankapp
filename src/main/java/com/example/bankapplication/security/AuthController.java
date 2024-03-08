package com.example.bankapplication.security;

import com.example.bankapplication.entity.Client;
import com.example.bankapplication.service.interfaces.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication controller")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ClientService clientService;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ClientService clientService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPass()));
            String email = authentication.getName();
            Client client = clientService.findClientByEmail(email);
            String token = jwtUtil.createToken(client);
            LoginResponse loginResponse = new LoginResponse(email, token);
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException | InternalAuthenticationServiceException | IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

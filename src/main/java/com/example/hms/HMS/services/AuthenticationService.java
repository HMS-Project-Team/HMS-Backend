package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.LoginDto;
import com.example.hms.HMS.dtos.requests.NewPasswordRequestDto;
import com.example.hms.HMS.dtos.responses.AuthenticationResponseDto;
import com.example.hms.HMS.entities.Token;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.PasswordMismatchException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.AuthenticationMapper;
import com.example.hms.HMS.repositories.AuthenticationRepository;
import com.example.hms.HMS.repositories.TokenRepository;
import com.example.hms.HMS.repositories.UserRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private AuthenticationRepository authenticationRepository;


    private static final String PASSWORD_SPQCIAL =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]).+$";

    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Transactional
    public AuthenticationResponseDto login(LoginDto request) {

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invalid credentials"
                ));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // Generate JWT token using User object
        String jwtToken = jwtService.generateToken(user.getEmail());

        // Save token
        Token token = new Token();
        token.setToken(jwtToken);
        token.setType("Bearer");
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plusSeconds(jwtExpiration / 1000));
        token.setRevoked(false);
        tokenRepository.save(token);

        // Map user to response
        AuthenticationResponseDto response = authenticationMapper.toAuthenticationResponse(user);

        // Set token details
        response.setAccessToken(jwtToken);
        response.setExpiresIn(jwtExpiration / 1000);

        return response;
    }

    public void newPasswordCheck(NewPasswordRequestDto newPasswordRequestDto) {

        if(!newPasswordRequestDto.getNewPassword().equals(newPasswordRequestDto.getConfirmPassword())){
            throw new PasswordMismatchException(ValidationMessages.PASSWORD_MISMATCH);
        }

        User user = authenticationRepository.findByEmail(newPasswordRequestDto.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));


        String oldPassword = user.getPassword();
        String newPassword = newPasswordRequestDto.getNewPassword();

        if(passwordEncoder.matches(newPassword , oldPassword)){
            throw  new IllegalArgumentException(ValidationMessages.NEW_PASSWORD_SAME_AS_OLD);
        }

        if (!newPassword.matches(PASSWORD_SPQCIAL)) {
            throw new IllegalArgumentException(ValidationMessages.PASSWORD_COMPLEXITY);
        }

        user.setPassword(passwordEncoder.encode(newPasswordRequestDto.getNewPassword()));
        authenticationRepository.save(user);
    }
}

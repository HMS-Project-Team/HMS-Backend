package com.example.hms.HMS.services;


import com.example.hms.HMS.dtos.requests.NewPasswordRequestDto;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.PasswordMismatchException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.repositories.AuthenticationRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String PASSWORD_SPQCIAL =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]).+$";


    public void newPasswordCheck(NewPasswordRequestDto newPasswordRequestDto) {

        if(!newPasswordRequestDto.getNewPassword().equals(newPasswordRequestDto.getConfirmPassword())){
            throw new PasswordMismatchException(ValidationMessages.PASSWORD_MISMATCH);
        }

        User user = authenticationRepository.findByEmail(newPasswordRequestDto.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));


        String oldPassword = user.getPassword();
        String newPassword = newPasswordRequestDto.getNewPassword();

        if(bCryptPasswordEncoder.matches(newPassword , oldPassword)){
            throw  new IllegalArgumentException(ValidationMessages.NEW_PASSWORD_SAME_AS_OLD);
        }

        if (!newPassword.matches(PASSWORD_SPQCIAL)) {
            throw new IllegalArgumentException(ValidationMessages.PASSWORD_COMPLEXITY);
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPasswordRequestDto.getNewPassword()));
        authenticationRepository.save(user);
    }
}

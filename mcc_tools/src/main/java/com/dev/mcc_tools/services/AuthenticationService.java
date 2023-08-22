package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.AuthRequest;
import com.dev.mcc_tools.controllers.AuthenticationResponse;
import com.dev.mcc_tools.controllers.ErrorResponse;
import com.dev.mcc_tools.controllers.FormattedResponse;
import com.dev.mcc_tools.domain.Role;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.UserRepository;
import com.dev.mcc_tools.security.JwtService;
import com.dev.mcc_tools.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserValidator userValidator = new UserValidator();


    public FormattedResponse login(AuthRequest request) {
        userValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = userValidator.getErrors();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userRepository.findByEmail(request.getEmail());
            AuthenticationResponse response = new AuthenticationResponse(user, jwtService.generateToken(user));
            return new FormattedResponse(httpStatus.value(), true, response);
        } catch (AuthenticationException e) {

            httpStatus = HttpStatus.UNAUTHORIZED;

            userValidator.setErrors("authentication", "username or password invalid");

            return new ErrorResponse(httpStatus.value(), false, errors);

        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ErrorResponse(httpStatus.value(), false, errors);

        }


    }
}

package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.AuthRequest;
import com.dev.mcc_tools.controllers.AuthenticationResponse;
import com.dev.mcc_tools.domain.Role;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.UserRepository;
import com.dev.mcc_tools.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail());
        return jwtService.generateToken(user);

    }
}

package com.spring.security.service;

import com.spring.security.dto.auth.AuthRequest;
import com.spring.security.dto.auth.AuthResponse;
import com.spring.security.entity.User;
import com.spring.security.exception.custom.CustomException;
import com.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Autowired
//    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
//    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) {

        return userRepo.findByUsername(username).orElseThrow(
                () -> new CustomException("Username is not found", HttpStatus.NOT_FOUND.value())
        );
    }



    public AuthResponse register(AuthRequest request) {

        Optional<User> optionalUser = userRepo.findByUsername(request.getUsername());

        if(optionalUser.isPresent()){
            throw new CustomException("Username already exists", HttpStatus.BAD_REQUEST.value());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles("ROLE_USER")
                .active(true)
                .build();

        user = userRepo.save(user);

        return new AuthResponse(user.getUsername(), User.getRoles(user.getRoles()));
    }


    public AuthResponse authenticate(AuthRequest request){

        try {
            // Authenticate the user with provided credentials
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Set the authenticated token in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Retrieve the authenticated user details
            User user = (User) authentication.getPrincipal();

            return new AuthResponse(user.getUsername(), User.getRoles(user.getRoles()));
        } catch (Exception e) {
            // Handle authentication failure
            throw new CustomException("Authentication failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED.value());
        }
    }




    public List<User> findAll() {
        return userRepo.findAll();
    }


    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

}

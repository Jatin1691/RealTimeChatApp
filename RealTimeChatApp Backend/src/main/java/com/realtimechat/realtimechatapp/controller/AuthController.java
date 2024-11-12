package com.realtimechat.realtimechatapp.controller;

import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.config.TokenProvider;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.repository.UserRepository;
import com.realtimechat.realtimechatapp.request.LoginRequest;
import com.realtimechat.realtimechatapp.response.AuthResponse;
import com.realtimechat.realtimechatapp.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    public ResponseEntity<AuthResponse> CreateUser(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();

        User user1=userRepository.findByEmail(email);
        if(user1!=null) {
            throw new UserException("User Already Registered"+user1);
        }

        User createUser=new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setFullName(fullName);

        userRepository.save(createUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=tokenProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse(jwt,true);

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);

    }

    private ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) throws UserException {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication=authenticateHandler(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=tokenProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse(jwt,true);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);

    }

    private Authentication authenticateHandler(String email,String password) throws UserException {
        UserDetails userDetails=customUserDetailService.loadUserByUsername(email);

        if(userDetails==null) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password or Username");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}

package com.pinquiry.api.config;

import com.pinquiry.api.model.User;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    UserService userService;


    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        User u = userService.findUserByUsername(auth.getName());

        if (u != null && u.getPassword() == auth.getCredentials()) {
            return new UsernamePasswordAuthenticationToken(auth.getName(),
                    auth.getCredentials());
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}

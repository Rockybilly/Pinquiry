package com.pinquiry.api.service;

import com.pinquiry.api.config.CustomUserDetailService;
import com.pinquiry.api.config.JwtTokenUtil;
import com.pinquiry.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    CustomUserDetailService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    public String  createAuthenticationToken(User u) throws Exception {


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(u.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }


    public String getUsernameFromToken(String token){
        return jwtTokenUtil.getUsernameFromToken(token);
    }
}

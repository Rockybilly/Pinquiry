package com.pinquiry.api.config;

import com.pinquiry.api.model.User;
import com.pinquiry.api.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userService.findUserByUsername(username);



        if (u == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(u);
    }
}

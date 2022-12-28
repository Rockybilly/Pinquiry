package com.pinquiry.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if(!Objects.equals(request.getMethod(), "OPTIONS")){
            String jwtToken = null;

            final Cookie[] a = request.getCookies();
            if (a != null) {
                for (Cookie cookie : a) {
                    if (Objects.equals(cookie.getName(), "jwt")) {
                        jwtToken = cookie.getValue();
                    }
                }

                String username = null;

                if (jwtToken != null) {
                    try {
                        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    }catch (Exception e){
                        System.out.println("no username with this token");
                    }


                    // validating the token
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                        UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

                        try{
                            jwtTokenUtil.validateToken(jwtToken, userDetails);
                        }catch (Exception e){
                            response.setHeader(HttpHeaders.SET_COOKIE, "jwt=null"  );
                            return;
                        }



                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    }
                }
            }

        }

        chain.doFilter(request, response);
    }

}

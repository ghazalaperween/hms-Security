package com.hms.Configure;

import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import com.hms.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;

    private AppUserRepository appUserRepository;

    public JWTFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if(token!=null&& token.startsWith("Bearer ")){// remove this bearer and star with 8
            String tokenVal = token.substring(8,token.length()-1);
            System.out.println(tokenVal);
            String username = jwtService.getUsername(tokenVal);
            //System.out.println(username);
            Optional<AppUser> opUsername = appUserRepository.findByusername(username);
            if(opUsername.isPresent()){
                //later
            }

        }

        filterChain.doFilter(request,response);

    }

}

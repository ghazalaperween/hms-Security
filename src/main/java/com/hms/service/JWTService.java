package com.hms.service;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.UnsupportedEncodingException;
//
//import java.util.Date;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hms.entity.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmkey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

//    @PostConstruct
//    public void postContruct(){
//        System.out.println(algorithmkey);
//        System.out.println(issuer);
//        System.out.println(expiryTime);
//    }

    private Algorithm algorithm; // jwt token  is start


    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmkey);
    }

    public String generateToken(String Username){
        return JWT.create()
                .withClaim("name",Username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);

    }

    public String getUsername(String token){
        DecodedJWT decodedJWT =
                JWT.require(algorithm).
                withIssuer(issuer).// verification the token
                        build().//jwt verifer
                        verify(token);
        return decodedJWT.getClaim("name").asString();


    }


}

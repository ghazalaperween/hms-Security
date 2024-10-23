package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private AppUserService appUserService;
    private  AppUserRepository appUserRepository;

    public UserController(AppUserService appUserService,
                          AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;

    }

    @PostMapping("/signup")
    public ResponseEntity<?> createuser(
            @RequestBody AppUser User
    ){
        Optional<AppUser> opusername = appUserRepository.findByusername(User.getUsername());
        if(opusername.isPresent()){
            return new ResponseEntity<>("username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(User.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String enCryptedPassword = BCrypt.hashpw(User.getPassword(), BCrypt.gensalt(5));
        User.setPassword(enCryptedPassword);
        AppUser Saveduser = appUserRepository.save(User);
        return new ResponseEntity<>(Saveduser, HttpStatus.CREATED);
    }

    @GetMapping("/message")
    public String getMessage(){
        return "hello";
    }

//    //http://localhost:8080/login
//    @PostMapping("/login")
//    public ResponseEntity<String> login(
//            @RequestBody LoginDto dto
//    ){
//        boolean status = appUserService.verifyLogin(dto);
//        if(status){
//            return new ResponseEntity<>("user Logined", HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
//        }
//    }
//
//    //http://localhost:8080/login
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDto dto){
//        String token = appUserService.verifyLogin(dto);
//        if(token!=null){
//            return new ResponseEntity<>(token, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
//        }
//    }

     //http://localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
        String token = appUserService.verifyLogin(dto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
        }
    }

}

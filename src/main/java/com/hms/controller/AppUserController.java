package com.hms.controller;


import com.hms.entity.AppUser;
import com.hms.payload.AppUserDto;
import com.hms.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/appusers")
public class AppUserController {

    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

//    @PostMapping
//    public ResponseEntity<AppUser> createAppUser(
//            @RequestBody AppUser appUser
//    ) {
//
//        AppUser app = appUserService.createAppUser(appUser);
//        return new ResponseEntity<>(app, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<AppUserDto> createAppUser(
            @RequestBody AppUserDto appUserDto
    ) {

        AppUserDto appUDto = appUserService.createAppUser(appUserDto);
        return new ResponseEntity<>(appUDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAppUser(
            @RequestParam Long id
    ) {
        appUserService.deleteAppUser(id);
        return new ResponseEntity<>("Delete", HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateAppUser(
            @PathVariable Long id,
            @RequestBody AppUser appUser
    ){
        AppUser updateApp = appUserService.updateAppUser(id,appUser);
        return new ResponseEntity<>(updateApp, HttpStatus.OK);
}

    @GetMapping
    public List<AppUser> getAllAppUser(){
        List<AppUser> appUser = appUserService.getAppUser();
        return appUser;
    }


}
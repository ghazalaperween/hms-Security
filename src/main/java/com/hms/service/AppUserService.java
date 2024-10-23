package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.payload.AppUserDto;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private AppUserRepository appUserRepository;
    private ModelMapper modelMapper;
    private JWTService jwtService;

    public AppUserService(AppUserRepository appUserRepository, ModelMapper modelMapper, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }



    public AppUser createAppUser(AppUser appUser) {
        AppUser savedEntity = appUserRepository.save(appUser);
        return savedEntity;
    }



    public void deleteAppUser(Long id) {
        appUserRepository.deleteById(id);
    }

    public AppUser updateAppUser(Long id, AppUser appUser) {
        AppUser a = appUserRepository.findById(id).get();
        a.setName(appUser.getName());
        a.setUsername(appUser.getUsername());
        a.setEmail(appUser.getEmail());
        a.setPassword(appUser.getPassword());
        AppUser saveEntity = appUserRepository.save(a);
        return saveEntity;
    }


    public List<AppUser> getAppUser() {
        List<AppUser> app = appUserRepository.findAll();
        return app;
    }

//    public AppUserDto createAppUser(AppUserDto appUserDto){
//          // Copy dto to Entity
//        AppUser appUser = new AppUser();
//        appUser.setName(appUserDto.getName());
//        appUser.setUsername(appUserDto.getUsername());
//        appUser.setEmail(appUserDto.getEmail());
//        appUser.setPassword(appUserDto.getPassword());
//
//        AppUser saveEntity = appUserRepository.save(appUser);
//              //Copy entity to dto

//        AppUserDto dto = new AppUserDto();
//        dto.setName(saveEntity.getName());
//        dto.setUsername(saveEntity.getUsername());
//        dto.setEmail(saveEntity.getEmail());
//        dto.setPassword(saveEntity.getPassword());
//
//        return dto;

//    }
            public AppUserDto createAppUser(AppUserDto appUserDto){
                AppUser appUser = mapToEntity((appUserDto));
                AppUser saveEntity = appUserRepository.save(appUser);

                AppUserDto dto = mapToDto(saveEntity);
                return dto;
            }

//        AppUser mapToEntity(AppUserDto appUserDto){
//        AppUser appUser = new AppUser();
//        appUser.setUsername(appUserDto.getUsername());
//        appUser.setEmail(appUserDto.getEmail());
//        appUser.setName(appUserDto.getName());
//        appUser.setPassword(appUserDto.getPassword());
//        return appUser;
//        }

            AppUser mapToEntity(AppUserDto appUserDto){
                 AppUser map = modelMapper.map(appUserDto, AppUser.class);
                 return map;
    }

//        AppUserDto mapToDto(AppUser appUser ){
//        AppUserDto dto = new AppUserDto();
//        dto.setUsername(appUser.getUsername());
//        dto.setEmail(appUser.getEmail());
//        dto.setName(appUser.getName());
//        dto.setPassword(appUser.getPassword());
//        return dto;
//        }

    AppUserDto mapToDto(AppUser appUser ){
        AppUserDto dto = modelMapper.map(appUser,AppUserDto.class);
        return dto;
    }

//    public boolean verifyLogin(LoginDto dto) {
//        Optional<AppUser> opuser = appUserRepository.findByusername(dto.getUsername());
//        if(opuser.isPresent()){
//            AppUser appUser = opuser.get();
//            return BCrypt.checkpw(dto.getPassword(),appUser.getPassword());
//        }else{
//            return false;
//        }
//    }


    public String verifyLogin(LoginDto dto){
        Optional<AppUser> opUser = appUserRepository.findByusername(dto.getUsername());
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();
            if(BCrypt.checkpw(dto.getPassword(),appUser.getPassword())){
                //generate token
                String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }
        }else{
            return null;
        }
        return null;
    }
}

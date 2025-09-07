package com.gameboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameboard.domain.AppUser;
import com.gameboard.domain.AppUserRepository;
import com.gameboard.domain.dto.AppUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public AppUserDto addUser(AppUserDto appUserDto) throws Exception {
        String encryptedPassword = passwordEncoder.encode(appUserDto.getPassword());

        AppUser appUser = AppUser.builder()
                .username(appUserDto.getUsername())
                .password(encryptedPassword)
                .nickname(appUserDto.getNickname())
                .role(appUserDto.getRole())
                .email(appUserDto.getEmail())
                .nickname(appUserDto.getNickname())
                .build();

//        AppUser appUser = objectMapper.convertValue(appUserDto, AppUser.class);

        AppUser savedAppUser = appUserRepository.save(appUser);
        appUserDto.setId(savedAppUser.getId());

        return appUserDto;
    }

    public AppUserDto login(AppUserDto appUserDto) throws Exception{
        String paramPassword = appUserDto.getPassword();

        AppUser appUser = appUserRepository.findByUsername(appUserDto.getUsername()).orElseThrow(() -> new Exception("아이디를 다시 확인해주세요."));
        boolean isMatchPassword = passwordEncoder.matches(paramPassword, appUser.getPassword());
        if(!isMatchPassword) throw new Exception("비밀번호가 틀렸는데? 어쩔티비~ 저쩔티비~");

        return appUserDto;
    }

    public AppUserDto findUserInfomatiton (String username) throws Exception {
        AppUser appUser = appUserRepository.findByUsername(username).orElseThrow(() -> new Exception("아이디를 다시 확인해주세요."));
        AppUserDto appUserDto = objectMapper.convertValue(appUser, AppUserDto.class);
        appUserDto.setPassword(null);

        return appUserDto;
    }
}



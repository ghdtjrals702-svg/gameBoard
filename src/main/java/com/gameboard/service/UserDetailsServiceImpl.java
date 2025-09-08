package com.gameboard.service;

import com.gameboard.domain.AppUser;
import com.gameboard.domain.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //이 메서드 쓰일때?
        // 실제 해당 username(ID)을 가지는 유저가 DB에 존재하는지 확인
        // + 해당 유저정보를 UserDetails 타입으로 반환하는 메서드 스프링 시큐리티가 우리한테 떠넘긴 부분


        Optional<AppUser> user =  appUserRepository.findByUsername(username);//아까 만들었던 쿼리 메서드

        UserDetails userDetails = null;
        if(user.isPresent()){
            AppUser appUser = user.get();
            userDetails = User.withUsername(username)
                    .password(user.get().getPassword())
                    .roles(appUser.getRole())
                    .build();

        }else { //널일 때
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;

    }
}

package com.gameboard.domain;

import com.gameboard.domain.dto.AppUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="app_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String role;

    @Column(unique = true)
    private String email;

    public void dtoToEntityUser(AppUserDto appUserDto) {
        this.username = appUserDto.getUsername();
        this.password = appUserDto.getPassword();
        this.nickname = appUserDto.getNickname();
        this.role = appUserDto.getRole();
        this.email = appUserDto.getEmail();
    }

}
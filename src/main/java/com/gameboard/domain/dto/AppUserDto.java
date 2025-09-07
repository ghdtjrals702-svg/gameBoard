package com.gameboard.domain.dto;

import com.gameboard.domain.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDto {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String role;
    private String email;

//    public AppUser dtoToEntityUser() {
//        return AppUser.builder()
//                .username(this.getUsername())
//                .password(this.getPassword())
//                .nickname(this.getNickname())
//                .role(this.getRole())
//                .email(this.getEmail())
//                .build();
//    }

}

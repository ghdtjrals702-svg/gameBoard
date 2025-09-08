package com.gameboard.domain.dto;

import com.gameboard.domain.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDto {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    private String role;
    @Email
    @NotBlank
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

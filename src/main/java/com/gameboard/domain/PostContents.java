package com.gameboard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="post_contents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostContents {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String name;
    private Integer date;
    private String content;
    private String view;
    private String img;

}

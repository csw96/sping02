package com.sparta.springproj02.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class UserInfo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private Long kakaoId;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
        this.kakaoId = null;
    }

    public UserInfo(String username, String password, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.kakaoId = kakaoId;
    }
}
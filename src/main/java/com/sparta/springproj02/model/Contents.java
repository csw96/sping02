package com.sparta.springproj02.model;

import com.sparta.springproj02.dto.ContentsPostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Contents extends  Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable =false)
    private String username;

    @Column(nullable =false)
    private String contents;


    public Contents(String username, String contents){
        this.username = username;
        this.contents = contents;
    }

    public void update(ContentsPostRequestDto requestDto){
        this.contents = requestDto.getContents();
    }

    public Contents(ContentsPostRequestDto requestDto, UserDetails userDetails){
        this.contents = requestDto.getContents();
        this.username = userDetails.getUsername();
    }
}
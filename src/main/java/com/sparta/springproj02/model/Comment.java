package com.sparta.springproj02.model;

import com.sparta.springproj02.dto.CommentPostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends  Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable =false)
    private String username;

    @Column(nullable =false)
    private String comment;

    @Column(nullable =false)
    private Long contentsId;

    public Comment(String username, String comment, Long contentsId){
        this.username = username;
        this.comment = comment;
        this.contentsId = contentsId;
    }

    public void update(CommentPostRequestDto requestDto, UserDetails userDetails){
        this.username = userDetails.getUsername();
        this.comment = requestDto.getComment();
    }

    public Comment(Long id, CommentPostRequestDto requestDto, UserDetails userDetails){
        this.username = userDetails.getUsername();
        this.comment = requestDto.getComment();
        this.contentsId = id;
    }
}

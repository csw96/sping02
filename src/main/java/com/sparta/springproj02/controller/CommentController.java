package com.sparta.springproj02.controller;

import com.sparta.springproj02.dto.CommentPostRequestDto;
import com.sparta.springproj02.model.Comment;
import com.sparta.springproj02.security.UserDetailsImpl;
import com.sparta.springproj02.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;}

    @GetMapping("/api/comment/{id}")
    public List<Comment> getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }


    @PostMapping("/api/comment/{id}")
    public String createComment(@PathVariable Long id,
                                @RequestBody CommentPostRequestDto commentPostRequestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, commentPostRequestDto, userDetails);

    }

    @PutMapping("/api/comment/{id}")
    public String updateComment(@PathVariable Long id,
                                @RequestBody CommentPostRequestDto commentPostRequestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(commentPostRequestDto, id, userDetails);
    }

    @DeleteMapping("/api/comment/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails);

    }

}
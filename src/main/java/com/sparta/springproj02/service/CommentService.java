package com.sparta.springproj02.service;

import com.sparta.springproj02.dto.CommentPostRequestDto;
import com.sparta.springproj02.model.Comment;
import com.sparta.springproj02.repository.CommentRepository;
import com.sparta.springproj02.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComment(Long id){
        return commentRepository.findByContentsIdOrderByModifiedAtDesc(id);
    }


    public String createComment(Long contentsId, CommentPostRequestDto commentPostRequestDto, UserDetailsImpl userDetails) {
        Comment comment = new Comment(contentsId, commentPostRequestDto, (UserDetails) userDetails);
        commentRepository.save(comment);
        return "등록완료";
    }

    @Transactional
    public String updateComment(CommentPostRequestDto commentPostRequestDto, Long id, UserDetails userDetails) {
        Comment comments = commentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        String msg;
        if(comments.getUsername().equals(userDetails.getUsername())){
            String comment = commentPostRequestDto.getComment();
            comments.setComment(comment);
            commentRepository.save(comments);
            msg = "수정완료";
        }else{
            msg = "자신의 댓글만 수정 가능합니다.";
        }
        return msg;
    }


    public String deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comments = commentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        String msg;
        if(comments.getUsername().equals(userDetails.getUsername())){
            commentRepository.deleteById(id);
            msg = "삭제완료";
        }else{
            msg = "자신의 댓글만 삭제 가능합니다.";
        }
        return msg;
    }
}
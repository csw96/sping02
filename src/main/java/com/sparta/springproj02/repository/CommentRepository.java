package com.sparta.springproj02.repository;

import com.sparta.springproj02.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>  {
    List<Comment> findByContentsIdOrderByModifiedAtDesc(Long id);
}
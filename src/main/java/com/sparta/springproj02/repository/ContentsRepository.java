package com.sparta.springproj02.repository;

import com.sparta.springproj02.model.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    List<Contents> findAllByOrderByModifiedAtDesc();
}
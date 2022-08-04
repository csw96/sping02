package com.sparta.springproj02.controller;


import com.sparta.springproj02.dto.ContentsPostRequestDto;
import com.sparta.springproj02.model.Contents;
import com.sparta.springproj02.security.UserDetailsImpl;
import com.sparta.springproj02.service.ContentsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class ContentsController {

    private final ContentsService contentsService;

    public ContentsController(ContentsService contentsService){
        this.contentsService = contentsService;
    }

    @GetMapping("/api/contents")
    public List<Contents> getContents() {
        return contentsService.getContents();
    }

    @GetMapping("/api/contents/{id}")
    public Optional<Contents> getContent(@PathVariable Long id){
        return contentsService.getContent(id);
    }

    @PostMapping("/api/contents")
    public String createContents(@RequestBody ContentsPostRequestDto contentsPostRequestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return contentsService.createContents(contentsPostRequestDto, userDetails);

    }

    @PutMapping("/api/contents/{id}")
    public String updateCotents(@PathVariable Long id,
                                @RequestBody ContentsPostRequestDto contentsPostRequestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return contentsService.updateContents(contentsPostRequestDto, id, userDetails);
    }

    @DeleteMapping("/api/contents/{id}")
    public String deleteContents(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return contentsService.deleteContents(id, userDetails);

    }

}
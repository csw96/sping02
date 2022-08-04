package com.sparta.springproj02.service;

import com.sparta.springproj02.dto.ContentsPostRequestDto;
import com.sparta.springproj02.model.Contents;
import com.sparta.springproj02.repository.ContentsRepository;
import com.sparta.springproj02.repository.ContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContentsService {

    private final ContentsRepository contentsRepository;

    @Autowired
    public ContentsService(ContentsRepository contentsRepository) {
        this.contentsRepository = contentsRepository;
    }

    public List<Contents> getContents(){
        return contentsRepository.findAllByOrderByModifiedAtDesc();
    }

    public Optional<Contents> getContent(Long id){
        return contentsRepository.findById(id);
    }


    public String createContents(ContentsPostRequestDto contentsPostRequestDto, UserDetails userDetails) {
        Contents contents = new Contents(contentsPostRequestDto, userDetails);
        contentsRepository.save(contents);
        return "등록완료";
    }

    @Transactional
    public String updateContents(ContentsPostRequestDto contentsPostRequestDto, Long id, UserDetails userDetails) {
        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));
        String msg;
        if(contents.getUsername().equals(userDetails.getUsername())){
            String content = contentsPostRequestDto.getContents();
            contents.setContents(content);
            contentsRepository.save(contents);
            msg = "수정완료";
        }else{
            msg = "자신의 게시물만 수정 가능합니다.";
        }
        return msg;
    }

    public String deleteContents(Long id, UserDetails userDetails) {
        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));
        String msg;
        if(contents.getUsername().equals(userDetails.getUsername())){
            contentsRepository.deleteById(id);
            msg = "삭제완료";
        }else{
            msg = "자신의 게시물만 삭제 가능합니다.";
        }
        return msg;
    }
}
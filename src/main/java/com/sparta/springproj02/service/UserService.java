package com.sparta.springproj02.service;

import com.sparta.springproj02.dto.SignupRequestDto;
import com.sparta.springproj02.model.UserInfo;
import com.sparta.springproj02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public String registerUser(SignupRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        Optional<UserInfo> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return "이미 사용중인 아이디입니다.";
        }
        if (!username.matches("^[a-zA-Z0-9]{3,}$")){
            return "아이디는 영문대소문자 및 숫자만으로 3글자이상으로 입력해야합니다.";
        }
        if (password.length()<4) {
            return "비밀번호는 4글자 이상으로 입력해야 합니다.";
        }
        if (password.contains(username)){
            return "비밀번호에 아이디가 포함될 수 없습니다.";
        }
        if (requestDto.getPassword().equals(requestDto.getPasswordCheck()) ){
            // 패스워드 암호화
            String encodepassword = passwordEncoder.encode(requestDto.getPassword());
            UserInfo user = new UserInfo(username, encodepassword);
            userRepository.save(user);
            return "회원가입 완료";
        }
        else {
            return "비밀번호가 일치하지 않습니다.";
        }

    }
}
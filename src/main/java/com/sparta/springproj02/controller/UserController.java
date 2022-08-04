package com.sparta.springproj02.controller;

import com.sparta.springproj02.dto.SignupRequestDto;
import com.sparta.springproj02.dto.UserInfoDto;
import com.sparta.springproj02.security.UserDetailsImpl;
import com.sparta.springproj02.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //로그인페이지
    @GetMapping("/user/loginView")
    public String login() {
        return "login";
    }

    //회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    //회원 가입 요청

    @PostMapping("/user/signup")
    @ResponseBody
    public String registerUser(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);

    }

    // 회원 관련 정보 받기
    @PostMapping("/user/userinfo")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        return new UserInfoDto(username);
    }

}
package com.sparta.springproj02.security;


import org.json.simple.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserLoginFailer implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String error = null;
        if (exception instanceof BadCredentialsException) {
            error = "아이디 또는 패스워드를 확인해주세요";
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject object = new JSONObject();
        object.put("message", error);
        response.getWriter().write(object.toString());
    }
}
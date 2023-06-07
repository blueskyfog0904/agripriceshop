package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.config.AppConfig;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
//    private final AppConfig appConfig;

    @GetMapping("/auth/login")
    public String login() {
        return "로그인 페이지입니다.";
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody MemberDto memberDto) {
        authService.signup(memberDto);
    }
}

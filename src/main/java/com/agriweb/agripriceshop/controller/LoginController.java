package com.agriweb.agripriceshop.controller;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }



}

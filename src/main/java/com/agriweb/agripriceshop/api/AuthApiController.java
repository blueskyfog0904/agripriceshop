package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.dto.LoginDto;
import com.agriweb.agripriceshop.jwt.TokenInfo;
import com.agriweb.agripriceshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiController {
    private final MemberService memberService;

    @PostMapping("/logintest")
    public TokenInfo login(@RequestBody LoginDto dto) {
        String memberId = dto.getLoginId();
        String password = dto.getPw();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }

}

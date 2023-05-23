package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.LoginDto;
import com.agriweb.agripriceshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    @Autowired
    private final MemberRepository memberRepository;

    public Member login(LoginDto dto) {
        Member login = memberRepository.findOneByLoginId(dto.getLoginId());
        return login;
    }
}

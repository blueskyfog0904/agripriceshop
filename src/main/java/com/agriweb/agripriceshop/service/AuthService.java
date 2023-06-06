package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.LoginDto;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(MemberDto memberDto) {
//        Member member = memberRepository.findOneByLoginId(memberDto.getLoginId());
//        if (member != null) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }


        String encryptedPassword = passwordEncoder.encode(memberDto.getPw());

        memberDto.setPw(encryptedPassword);
        Member target = Member.createMember(memberDto);
        memberRepository.save(target);

    }
}

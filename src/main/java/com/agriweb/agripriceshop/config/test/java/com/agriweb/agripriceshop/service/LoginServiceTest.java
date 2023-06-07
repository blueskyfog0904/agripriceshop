package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.LoginDto;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class LoginServiceTest {

    @Autowired MemberService memberService;
    @Autowired LoginService loginService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 로그인() throws  Exception {
        // given
        MemberDto dto = new MemberDto();
        dto.setUserName("kim");
        dto.setPw("1234");
        dto.setLoginId("kim1234");
        dto.setBirthdate(LocalDate.ofEpochDay(19850904));
        dto.setGender("남");
        dto.setEmail("kim1234@naver.com");
        dto.setTel("01012341234");
        Member member = Member.createMember(dto);
        Member member2 = memberService.join(member);


        // when
        LoginDto loginDto = new LoginDto();
        loginDto.setLoginId("kim1234");
        loginDto.setPw("1234");
        Member login = loginService.login(loginDto);

        // then
        Assertions.assertEquals(login, member2);

    }


}

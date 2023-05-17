package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setUserName("kim");
        member.setPw("1234");
        member.setLoginId("kim1234");
        member.setBirthdate(LocalDate.ofEpochDay(19850904));
        member.setGender("남");
        member.setEmail("kim1234@naver.com");
        member.setTel("01012341234");


        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberRepository.findOne(saveId));

    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setUserName("kim");
        member1.setPw("1234");
        member1.setLoginId("kim1234");
        member1.setBirthdate(LocalDate.ofEpochDay(19850904));
        member1.setGender("남");
        member1.setEmail("kim1234@naver.com");
        member1.setTel("01012341234");

        Member member2 = new Member();
        member2.setUserName("park");
        member2.setPw("1234");
        member2.setLoginId("kim1234");
        member2.setBirthdate(LocalDate.ofEpochDay(19850903));
        member2.setGender("남");
        member2.setEmail("kim12345@naver.com");
        member2.setTel("01012341234");

        //when
        memberService.join(member1);

        //then
        IllegalStateException thrown =
                Assertions.assertThrows(IllegalStateException.class,
                        ()-> memberService.join(member2));
        Assertions.assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());

    }

}
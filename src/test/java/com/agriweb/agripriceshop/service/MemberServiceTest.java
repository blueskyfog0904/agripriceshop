package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Member;
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
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        MemberDto dto = new MemberDto();
        dto.setUserName("kim");
        dto.setPw("1234");
        dto.setLoginId("kim1234");
        dto.setBirthdate(LocalDate.ofEpochDay(19850904));
        dto.setGender("남");
        dto.setEmail("kim1234@naver.com");
        dto.setTel("01012341234");
        Member member = Member.createMember(dto);


        //when
        Member member2 = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberRepository.findOne(member2.getId()));

    }
    @Test
    public void 회원삭제() throws Exception {
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
        Member member1 = memberService.join(member);

        // when
        memberService.delete(member1.getId());

        // then
        Assertions.assertEquals(null, memberRepository.findOne(member1.getId()));

    }


    @Test
    public void 회원수정업데이트() throws Exception {
        // given
        Member member1 = new Member();
        member1.setUserName("kim");
        member1.setPw("1234");
        member1.setLoginId("kim1234");
        member1.setBirthdate(LocalDate.ofEpochDay(19850904));
        member1.setGender("남");
        member1.setEmail("kim1234@naver.com");
        member1.setTel("01012341234");

        MemberDto dto = new MemberDto();
        dto.setPw("4321");
        dto.setTel("01043214321");
        dto.setAddr("전북 남원시");
        dto.setEmail("ahn1234@naver.com");

        // when
        Member member2 = memberService.join(member1);
        Member member3 = memberService.update(member1.getId(), dto);

        // then
        Assertions.assertEquals(member1, memberRepository.findOne(member2.getId()));

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
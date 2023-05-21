package com.agriweb.agripriceshop.controller;

import com.agriweb.agripriceshop.domain.Address;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/members/join")
    public String create(@RequestBody MemberDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";  // 회원가입 페이지로 이동
        }

        Member member = Member.createMember(dto);
        memberService.join(member);
        return "redirect:/";

    }



}

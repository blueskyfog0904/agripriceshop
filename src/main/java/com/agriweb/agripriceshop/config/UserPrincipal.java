package com.agriweb.agripriceshop.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal extends User {

    private final Long memberId;

    // role: 역할 -> 관리자, 사용자, 매니저
    //authority : 권한 -> 글쓰기, 글 읽기, 사용자정지시키기

    public UserPrincipal(com.agriweb.agripriceshop.domain.Member member) {
        super(member.getLoginId(), member.getPw(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        this.memberId = member.getId();
    }

    public Long getMemberId() {
        return memberId;
    }
}

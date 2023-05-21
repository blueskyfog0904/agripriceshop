package com.agriweb.agripriceshop.domain;

import com.agriweb.agripriceshop.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @Column(unique = true, length = 30)
    private String loginId;

    @Column(length = 50)
    private String pw;

    @Column(length = 10)
    private String userName;

    @Column
    private LocalDate birthdate;

    @Column
    private String gender;

    @Column
    private String tel;

    @Column
    private String addr;

    @Column
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    @OneToMany(mappedBy = "member")
    private List<Board> boards;

    //== 생성 메서드==//
    public static Member createMember(MemberDto dto){
        Member member = new Member();
        member.setLoginId(dto.getLoginId());
        member.setPw(dto.getPw());
        member.setUserName(dto.getUserName());
        member.setBirthdate(dto.getBirthdate());
        member.setGender(dto.getGender());
        member.setTel(dto.getTel());
        member.setAddr(dto.getAddr());
        member.setEmail(dto.getEmail());

        return member;
    }



}

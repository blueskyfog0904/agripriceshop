package com.agriweb.agripriceshop.domain;

import com.agriweb.agripriceshop.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(unique = true, length = 30)
    private String loginId;

    @Column(length = 400)
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //== 생성 메서드==//
    public static Member createMember(MemberDto dto){
        Member member = new Member();
        member.setId(dto.getId());
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

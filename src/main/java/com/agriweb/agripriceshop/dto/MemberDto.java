package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Address;
import com.agriweb.agripriceshop.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Data
@Builder
@Schema(description = "Member 관련 Dto")
public class MemberDto {

    @Schema(description = "Member Id")
    private Long id;

    @Schema(description = "Member loginId")
    private String loginId;

    @Schema(description = "Member 비밀번호")
    private String pw;

    @Schema(description = "Member 이름")
    private String userName;

    @Schema(description = "Member 생년월일")
    private LocalDate birthdate;

    @Schema(description = "Member 성별")
    private String gender;

    @Schema(description = "Member 전화번호")
    private String tel;

    @Schema(description = "Member 주소")
    private String addr;

    @Schema(description = "Member 이메일")
    private String email;

    @Schema(description = "Member ROLE")
    private String role;


    //== 생성 메서드==//
    public static MemberDto createMemberDto(Member member) {
        return new MemberDto(member.getId(), member.getLoginId(), member.getPw(), member.getUsername(), member.getBirthdate(), member.getGender(), member.getTel(), member.getAddr(), member.getEmail(), member.getRole());
    }



}

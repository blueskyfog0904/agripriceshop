package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Address;
import com.agriweb.agripriceshop.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
@Data
@Builder
@Schema(description = "Member 관련 Dto")
public class MemberDto {

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

    public Member toEntity() {
        Member member = Member.builder()
                .loginId(this.loginId)
                .pw(this.pw)
                .userName(this.userName)
                .birthdate(this.birthdate)
                .gender(this.gender)
                .tel(this.tel)
                .addr(this.addr)
                .email(this.email)
                .build();

        return member;
    }

}

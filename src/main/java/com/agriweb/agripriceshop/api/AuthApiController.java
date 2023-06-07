package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.LoginDto;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.jwt.TokenInfo;
import com.agriweb.agripriceshop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiController {
    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    // 회원 가입 API
    @Operation(summary = "auth 회원가입 메서드", description = "auth회원가입 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/api/auth/signup")
    public ResponseEntity<MemberDto> signup(@RequestBody MemberDto memberDto){


        Member member = Member.createMember(memberDto);
        Member created = memberService.join(member);
        MemberDto createdDto = MemberDto.createMemberDto(created);
        // 성공시 member 객체 반환
        return (createdDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @Operation(summary = "auth 로그인 메서드", description = "auth로그인 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/api/auth/login")
    public TokenInfo login(@RequestBody LoginDto dto) {
        String memberId = dto.getLoginId();
        String password = dto.getPw();

        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;

    }

}

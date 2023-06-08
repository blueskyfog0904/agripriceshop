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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 로그인 API
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

    // 전체 회원 조회 API
    @Operation(summary = "전체회원조회 메서드", description = "전체회원조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/user/members/list")
    public List<Member> list() {
        return memberService.findMembers();
    }

    // 회원 한명 조회 API
    @Operation(summary = "회원 한명 조회 메서드", description = "회원 한명 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/user/members/{loginId}")
    public Member indexOne(@PathVariable String loginId) {
        return memberService.findOnebyLoginId(loginId);
    }

    // 회원 수정 API
    @Operation(summary = "회원정보수정 메서드", description = "회원정보수정 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PutMapping("/user/members/{loginId}")
    public ResponseEntity<Member> updateMember(@PathVariable String loginId,
                                               @RequestBody MemberDto dto){

        Member updated = memberService.update(loginId, dto);
        return (updated !=null) ? ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 회원 삭제 API
    @Operation(summary = "회원정보삭제 메서드", description = "회원정보삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @DeleteMapping("/admin/members/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        Member target = memberService.findOne(id);
        String loginId = target.getLoginId();
        memberService.delete(id);
        Member delete = memberService.findOne(id);

        return (delete == null) ? ResponseEntity.ok(loginId + " 회원정보가 삭제되었습니다.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원정보 삭제에 실패하였습니다.");

    }


}

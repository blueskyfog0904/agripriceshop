package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.LoginDto;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.jwt.TokenInfo;
import com.agriweb.agripriceshop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Auth(회원가입,로그인)", description = "Auth(회원가입,로그인) 관련 api 입니다")
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
    @PostMapping("/api/common/auth/signup")
    public ResponseEntity<MemberDto> signup(@RequestBody MemberDto memberDto) {


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
    @PostMapping("/api/common/auth/login")
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
    @GetMapping("/api/user/members/list")
    public Page<Member> list(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy

    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return memberService.findMembers(pageable);
    }

    // 회원 한명 조회 API
    @Operation(summary = "회원 한명 조회 메서드", description = "회원 한명 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/user/members/{loginId}")
    public Member indexOne(@PathVariable String loginId) {
        return memberService.findOnebyLoginId(loginId);
    }

    // 회원 수정 API
    @Operation(summary = "회원정보수정 메서드", description = "회원정보수정 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PutMapping("/api/user/members/{loginId}")
    public ResponseEntity<Member> updateMember(@PathVariable String loginId,
                                               @RequestBody MemberDto dto) {

        Member updated = memberService.update(loginId, dto);
        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 회원 삭제 API
    @Operation(summary = "회원정보삭제 메서드", description = "회원정보삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @DeleteMapping("/api/admin/members/{loginId}")
    public ResponseEntity<String> delete(@PathVariable String loginId) {

        Member target = memberService.findOnebyLoginId(loginId);
        if (target != null) {
            String deletedLoginId = memberService.delete(target);
            return (deletedLoginId != null) ? ResponseEntity.ok(deletedLoginId + " 회원정보가 삭제되었습니다.") :
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 삭제에 실패하였습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }


    }
}
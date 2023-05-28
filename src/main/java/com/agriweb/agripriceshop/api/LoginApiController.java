package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.LoginDto;
import com.agriweb.agripriceshop.service.LoginService;
import com.agriweb.agripriceshop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="로그인", description = "로그인 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginApiController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final LoginService loginService;

    @Operation(summary = "로그인 메서드", description = "로그인 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping(value = "/api/login")
    public ResponseEntity<Member> loginMember(@RequestBody LoginDto dto) {
        // 멤버 조회 데이터 가져오기
        Member login = loginService.login(dto);

        // dto로 전송된 ID, PW 비교하기
        // 일치하면 login 성공, 불일치하면 비성공
        if (login.getId().equals(dto.getLoginId()) && login.getPw().equals(dto.getPw())) {
            return ResponseEntity.status(HttpStatus.OK).body(login);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }




}

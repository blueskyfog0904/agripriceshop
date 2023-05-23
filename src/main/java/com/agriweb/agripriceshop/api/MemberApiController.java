package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Tag(name="회원", description = "회원 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/members")
public class MemberApiController {

    @Autowired
    private final MemberService memberService;

    // 회원 가입 API
    @Operation(summary = "회원가입 메서드", description = "회원가입 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping(value = "join")
    public ResponseEntity<Member> createMember(@RequestBody MemberDto dto) {
        // Member 엔티티 생성
        Member member = Member.createMember(dto);
        // Member 엔티티 저장(DB)
        memberService.join(member);
        // 성공시 member 객체 반환
        return (member != null) ?
                ResponseEntity.status(HttpStatus.OK).body(member) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 전체 회원 조회 API
    @Operation(summary = "전체회원조회 메서드", description = "전체회원조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping(value = "index")
    public List<Member> index() {
        return memberService.findMembers();
    }

    // 회원 한명 조회 API
    @Operation(summary = "회원 한명 조회 메서드", description = "회원 한명 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping(value = "{id}")
    public Member indexOne(@PathVariable Long id) {
        return memberService.findOne(id);
    }

    // 회원 수정 API
    @Operation(summary = "회원정보수정 메서드", description = "회원정보수정 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PutMapping(value = "{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id,
                                               @RequestBody MemberDto dto){

        Member updated = memberService.update(id, dto);
        return (updated !=null) ? ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    // 회원 삭제 API
    @Operation(summary = "회원정보삭제 메서드", description = "회원정보삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Member> delete(@PathVariable Long id) {

        Member deleted = memberService.delete(id);
        return (deleted != null)? ResponseEntity.status(HttpStatus.OK).body(deleted):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }



}



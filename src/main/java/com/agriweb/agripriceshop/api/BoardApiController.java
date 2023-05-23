package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.BoardDto;
import com.agriweb.agripriceshop.service.BoardService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="게시판", description = "게시판 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {

    @Autowired
    private final BoardService boardService;

    @Autowired
    private final MemberService memberService;

    // 게시글 작성 API
    @Operation(summary = "게시글 작성 메서드", description = "게시글 작성 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/api/boards")
    public ResponseEntity<Board> create(@RequestBody BoardDto dto, String loginId) {
        Member member = memberService.findOnebyLoginId(loginId);
        Board board = Board.createBoard(dto, member);
        Board created = boardService.create(board);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 전체 글 조회 API
    @Operation(summary = "게시글 전체 조회 작성 메서드", description = "게시글 전체 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/boards")
    public List<Board> index() {
        return boardService.findBoards();
    }

    // 회원ID로 게시글 조회 api
    @Operation(summary = "회원 ID로 게시글 조회 작성 메서드", description = "회원 ID로 게시글 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/boards/{loginId}")
    public List<Board> findByLoginId(@PathVariable String loginId) {
        return boardService.findByLoginId(loginId);
    }










}

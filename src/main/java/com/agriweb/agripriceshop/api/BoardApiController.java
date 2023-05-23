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
import org.apache.coyote.Response;
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

    // 게시글 수정 API
    @Operation(summary = "게시글 수정 메서드", description = "게시글 수정 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody BoardDto dto) {
        Board updated = boardService.update(id, dto);
        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 게시글 삭제 API
    @Operation(summary = "게시글 삭제 메서드", description = "게시글 삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @DeleteMapping("/api/boards/{postId}")
    public ResponseEntity<String> delete(@PathVariable Long boardId) {

        if (boardService.findOne(boardId) != null ) {
            boardService.delete(boardId);
            return ResponseEntity.ok("게시글이 삭제되었습니다");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }


    }












}

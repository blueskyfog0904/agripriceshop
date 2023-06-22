package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.config.SecurityUtil;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @PostMapping("/api/user/boards")
    public ResponseEntity<BoardDto> create(@RequestBody BoardDto dto) {
        String loginId = SecurityUtil.getCurrentLoginId();
        Member member = memberService.findOnebyLoginId(loginId);
        Board board = Board.createBoard(dto, member);
        Board created = boardService.create(board);
        BoardDto createdDto = BoardDto.createBoardDto(created, member);
        return (createdDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

//    // 전체 글 조회 API
//    @Operation(summary = "게시글 전체 조회 작성 메서드", description = "게시글 전체 조회 메서드입니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "successful operation"),
//            @ApiResponse(responseCode = "400", description = "bad request operation")
//    })
//    @GetMapping("/api/boards")
//    public List<Board> list() {
//        return boardService.findBoards();
//    }

    // 전체 글 조회 API(페이징)
    @Operation(summary = "게시글 전체 조회 작성 메서드", description = "게시글 전체 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/common/boards")
    public Page<Board> list(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
            ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return boardService.findBoards(pageable);
    }

    // 회원ID로 게시글 조회 api
    @Operation(summary = "회원 ID로 게시글 조회 작성 메서드", description = "회원 ID로 게시글 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/common/boards/{loginId}")
    public Page<Board> findByLoginId(@PathVariable String loginId,
                 @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                 @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
                                     ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return boardService.findByLoginId(loginId, pageable);
    }

    // 게시글 상세 조회 API
    @Operation(summary = "게시글 상세조회 메서드", description = "게시글 상세조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/common/boards/{boardId}")
    public Board indexOne(@PathVariable long boardId) {
        return boardService.findOne(boardId);
    }


    // 게시글 수정 API
    @Operation(summary = "게시글 수정 메서드", description = "게시글 수정 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PutMapping("/api/user/boards/{id}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable Long id, @RequestBody BoardDto dto) {
        Board updated = boardService.update(id, dto);
        BoardDto updatedDto = BoardDto.updateBoardDto(updated);
        return (updatedDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updatedDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    // 게시글 삭제 API
    @Operation(summary = "게시글 삭제 메서드", description = "게시글 삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @DeleteMapping("/api/user/boards/{boardId}")
    public ResponseEntity<String> delete(@PathVariable Long boardId) {

        if (boardService.findOne(boardId) != null ) {
            boardService.delete(boardId);
            return ResponseEntity.ok("게시글이 삭제되었습니다");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }


    }












}

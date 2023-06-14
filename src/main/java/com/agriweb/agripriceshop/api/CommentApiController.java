package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.config.SecurityUtil;
import com.agriweb.agripriceshop.dto.CommentDto;
import com.agriweb.agripriceshop.dto.LoginRequest;
import com.agriweb.agripriceshop.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글", description = "댓글 관련 api 입니다")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // 댓글 생성 API
    @Operation(summary = "댓글 작성 메서드", description = "댓글 작성 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/api/user/boards/{boardId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long boardId, @RequestBody CommentDto dto
                                             ) {

        String loginId = SecurityUtil.getCurrentLoginId();
        if(loginId == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("LoginId를 찾을 수 없습니다.");
        }
        CommentDto createDto = commentService.create(boardId, loginId, dto);

        return (createDto != null) ? ResponseEntity.status(HttpStatus.OK).body(createDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    // 게시글에 대한 댓글 가져오기 API
    @Operation(summary = "게시글 댓글 가져오기 메서드", description = "게시글 댓글 가져오기 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/common/boards/{boardId}/comments")
    public Page<CommentDto> comments(@PathVariable Long boardId,
                 @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                 @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy

    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return commentService.comments(boardId, pageable);

    }

    // 댓글 수정 API
    @Operation(summary = "댓글 수정 메서드", description = "댓글 수정 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PutMapping("/api/user/comments/{commentId}")
    public ResponseEntity<CommentDto> update(@PathVariable Long commentId, @RequestBody CommentDto dto) {
        CommentDto updateDto = commentService.update(commentId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);

    }

    // 댓글 삭제 API
    @Operation(summary = "댓글 삭제 메서드", description = "댓글 삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @DeleteMapping("/api/user/comments/{commentId}")
    public ResponseEntity<String> delete(@PathVariable Long commentId) {

        CommentDto deleteDto = commentService.delete(commentId);

        return ResponseEntity.status(HttpStatus.OK).body("댓글이 삭제되었습니다.");
    }


}

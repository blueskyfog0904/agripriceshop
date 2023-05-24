package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.dto.CommentDto;
import com.agriweb.agripriceshop.dto.LoginRequest;
import com.agriweb.agripriceshop.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/api/boards/{boardId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long boardId, @RequestBody CommentDto dto,
                                             @RequestBody LoginRequest loginRequest) {
        String loginId = loginRequest.getLoginId();
        CommentDto createDto = commentService.create(boardId, loginId, dto);

        return (createDto != null) ? ResponseEntity.status(HttpStatus.OK).body(createDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

}

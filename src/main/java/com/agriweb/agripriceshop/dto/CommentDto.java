package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class CommentDto {
    private Long id;
    private String cmContent;
    private Long boardId;
    private Long memberId;
    private String loginId;
    private LocalDateTime regdate;
    private LocalDateTime update;
    private LocalDateTime deleteDate;

    public static CommentDto createCommentDto(Comment c) {
        return new CommentDto(c.getId(), c.getCmContent(), c.getBoard().getId(), c.getMember().getId(), c.getMember().getLoginId()
                , c.getRegdate(), c.getUpdate(), c.getDeleteDate());
    }

}

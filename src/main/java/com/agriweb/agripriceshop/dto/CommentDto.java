package com.agriweb.agripriceshop.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class CommentDto {
    private Long commentId;
    private String cmContent;
    private Long boardId;
    private Long memberId;
    private LocalDateTime regdate;
    private LocalDateTime update;
    private LocalDateTime deleteDate;

}

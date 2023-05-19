package com.agriweb.agripriceshop.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class BoardDto {

    private String boardId;
    private String boardTitle;
    private String boardContent;
    private String memberId;
    private LocalDateTime regdate;
    private LocalDateTime update;
    private LocalDateTime deleteDate;
    private int viewCount;

}

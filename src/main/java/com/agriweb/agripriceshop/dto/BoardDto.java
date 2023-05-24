package com.agriweb.agripriceshop.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Data
@Builder
public class BoardDto {

    private String boardId;
    private String boardTitle;
    private String boardContent;
    private Long memberId;
    private LocalDateTime regdate;
    private LocalDateTime update;
    private LocalDateTime deleteDate;
    private int viewCount;

}

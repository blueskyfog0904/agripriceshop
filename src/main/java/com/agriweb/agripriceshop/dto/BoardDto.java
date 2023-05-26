package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Board;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Data
@Builder
public class BoardDto {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private Long memberId;
    private LocalDateTime regdate;
    private LocalDateTime update;
    private LocalDateTime deleteDate;
    private int viewCount;

    //== 생성 메서드==//
    public static BoardDto createBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getId());
        boardDto.setBoardTitle(board.getBoardTitle());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setRegdate(LocalDateTime.now());
        if (board.getRegdate() != null) {
            boardDto.setUpdate(LocalDateTime.now());
        }
        return boardDto;
    }


}

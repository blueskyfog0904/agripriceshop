package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.domain.Member;
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
    // member loginId 추가(5/28)
    private String loginId;
    private LocalDateTime regdate;
    private LocalDateTime update;
    private LocalDateTime deleteDate;
    private int viewCount;

    //== 생성 메서드==//
    public static BoardDto createBoardDto(Board board, Member member) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getId());
        boardDto.setBoardTitle(board.getBoardTitle());
        boardDto.setMemberId(member.getId());
        boardDto.setLoginId(member.getLoginId());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setRegdate(LocalDateTime.now());
        if (board.getRegdate() != null) {
            boardDto.setUpdate(LocalDateTime.now());
        }
        return boardDto;
    }

    public static BoardDto updateBoardDto(Board board) {
        BoardDto updated = new BoardDto();
        updated.setBoardTitle(board.getBoardTitle());
        updated.setBoardContent(board.getBoardContent());
        return updated;
    }

}

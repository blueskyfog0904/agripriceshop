package com.agriweb.agripriceshop.domain;

import com.agriweb.agripriceshop.dto.BoardDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @Column(length = 50)
    private String boardTitle;

    @Column(length = 5000)
    private String boardContent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime regdate;

    private LocalDateTime update;

    private LocalDateTime deleteDate;

    @ColumnDefault("0")
    private int viewCount;

    //==생성 메서드==//
    public static Board createBoard(BoardDto dto, Member member) {
        Board board = new Board();
        board.setBoardTitle(dto.getBoardTitle());
        board.setBoardContent(dto.getBoardContent());
        board.setMember(member);
        board.setRegdate(LocalDateTime.now());
        return board;
    }


}

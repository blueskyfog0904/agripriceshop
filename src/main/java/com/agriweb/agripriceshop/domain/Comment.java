package com.agriweb.agripriceshop.domain;

import com.agriweb.agripriceshop.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Comment {
    @Id @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    private String cmContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime regdate;

    private LocalDateTime update;

    private LocalDateTime deleteDate;

    // Comment 생성
    public static Comment createComment(CommentDto dto, Board board, Member member) {
        // 예외 처리
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (dto.getBoardId() != board.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");

        // 엔티티 생성 및 반환
        return new Comment(dto.getId(), dto.getCmContent(), board, member, LocalDateTime.now(), null, null);
    }

    // Comment 수정
    public void patch(CommentDto dto) {
        // 예외발생
        if(this.id != dto.getId()) throw new IllegalArgumentException("댓글 수정 실패. 잘못된 ID 입니다.");

        // 객체를 갱신
        if (dto.getCmContent() != null) {
            this.cmContent = dto.getCmContent();
        }
        this.regdate = LocalDateTime.now();

    }


}

package com.agriweb.agripriceshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
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

}

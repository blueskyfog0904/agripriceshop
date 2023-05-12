package com.agriweb.agripriceshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    private Long boardId;

    private String writer;

    private LocalDateTime regdate;

    private LocalDateTime update;

    private LocalDateTime deleteDate;

}

package com.agriweb.agripriceshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String loginId;

    private String pw;

    private String userName;

    private LocalDate birthdate;

    private String gender;

    private String tel;

    private Address addr;

    private String email;

    private List<Order> orders;

    private List<Board> boards;


}

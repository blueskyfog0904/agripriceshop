package com.agriweb.agripriceshop.domain;

import jakarta.persistence.*;
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

    @Column(unique = true, length = 30)
    private String loginId;

    @Column(length = 50)
    private String pw;

    @Column(length = 10)
    private String userName;

    @Column
    private LocalDate birthdate;

    @Column
    private String gender;

    @Column
    private String tel;

    @Embedded
    private Address addr;

    @Column
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    @OneToMany(mappedBy = "member")
    private List<Board> boards;


}

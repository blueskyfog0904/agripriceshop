package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
@Data
public class MemberDto {

    private String loginId;
    private String pw;
    private String userName;
    private LocalDate birthdate;
    private String gender;
    private String tel;
    private String city;
    private String street;
    private String zipcode;
    private String email;

}

package com.agriweb.agripriceshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Data
@Builder
@Schema(description = "login 관련 Dto")
public class LoginDto {

    @Schema(description = "LoginId")
    private String loginId;
    @Schema(description = "pw")
    private String pw;

}

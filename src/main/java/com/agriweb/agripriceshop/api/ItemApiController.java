package com.agriweb.agripriceshop.api;


import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.ItemDto;
import com.agriweb.agripriceshop.service.ItemService;
import com.agriweb.agripriceshop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name="아이템(상품)", description = "아이템(상품) 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemApiController {

    @Autowired
    private final ItemService itemService;

    @Autowired
    private final MemberService memberService;

    //     아이템(상품) 등록 API
    @Operation(summary = "아이템(상품) 등록 메서드", description = "아이템(상품) 등록 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/api/items")
    public ResponseEntity<ItemDto> create(@RequestBody ItemDto dto, String loginId) {
        Member loginMember = memberService.findOnebyLoginId(loginId);
        dto.setRegdate(LocalDateTime.now());
        Item target = Item.createItem(dto, loginMember);
        Item saved = itemService.create(target);
        ItemDto created = ItemDto.createItemDto(saved, loginId);


    }


}

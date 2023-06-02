package com.agriweb.agripriceshop.api;


import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.ItemCategory;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        // loginId를 이용해서 Member 정보 가져오기
        Member loginMember = memberService.findOnebyLoginId(loginId);
        // ItemDto에 Regdate에 현재시간 입력
        dto.setRegdate(LocalDateTime.now());
        // Item 엔티티 생성
        Item target = Item.createItem(dto, loginMember);
        // 생성한 Item 엔티티를 저장
        Item saved = itemService.create(target);
        // return 해줄 ItemDto를 생성
        ItemDto createdDto = ItemDto.createItemDto(saved, loginId);
        return (createdDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    //     아이템(상품) 전체 조회 API
    @Operation(summary = "아이템(상품) 전체 조회 메서드", description = "아이템(상품) 전체 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/items")
    public List<ItemDto> index() {
        return itemService.findItems();
    }

    // 아이템(상품) 카테고리로 조회 API
    @Operation(summary = "아이템(상품) 카테고리로 조회 메서드", description = "아이템(상품) 카테고리로 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/items/category")
    public List<ItemDto> indexByCategory(@RequestParam ItemCategory itemCategory) {
        return itemService.findItemsByCategory(itemCategory);

    }

    // 아이템(상품) 상품이름 검색으로 조회 API
    @Operation(summary = "아이템(상품) 상품이름 검색으로 조회 메서드", description = "아이템(상품) 상품이름 검색으로 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/items/findname")
    public List<ItemDto> indexByCategory(@RequestParam String findName) {
        return itemService.findItemsByName(findName);
    }



    // 아이템(상품) 삭제 API
    @Operation(summary = "아이템(상품) 삭제 메서드", description = "아이템(상품) 삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @DeleteMapping("/api/items/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Item target = itemService.findOnebyId(id);
        String name = target.getName();
        if (target != null) {
            itemService.delete(id);
            return ResponseEntity.ok(name + " 상품이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 상품입니다.");
        }

    }







}

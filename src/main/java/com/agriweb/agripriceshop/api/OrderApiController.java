package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.domain.Order;
import com.agriweb.agripriceshop.dto.OrderSearch;
import com.agriweb.agripriceshop.service.ItemService;
import com.agriweb.agripriceshop.service.MemberService;
import com.agriweb.agripriceshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="주문", description = "주문 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApiController {
    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberService memberService;

    //     주문 등록 API
    @Operation(summary = "주문 등록 메서드", description = "주문 등록 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/user/order")
    public ResponseEntity<String> order(@RequestParam("memberId") Long memberId,
                                @RequestParam("itemId") Long itemId,
                                @RequestParam("count") int count) {
        Long ordered = orderService.order(memberId, itemId, count);
        return (ordered != null)? ResponseEntity.ok("주문이 완료되었습니다."):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문이 실패하였습니다.");
    }

    @Operation(summary = "주문 조회 메서드", description = "주문 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/user/orders")
    public List<Order> orderList(@RequestParam OrderSearch orderSearch) {
        return orderService.findOrders(orderSearch);

    }
    @Operation(summary = "주문 취소 메서드", description = "주문 취소 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/user/orders/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId ) {
        orderService.cancelOrder((orderId));
        Order deleted = orderService.findOne(orderId);
        return (deleted == null) ?
                ResponseEntity.ok("주문이 취소되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문 취소가 실패하였습니다.");
        }

}


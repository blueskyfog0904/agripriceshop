package com.agriweb.agripriceshop.api;

import com.agriweb.agripriceshop.config.SecurityUtil;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.domain.Order;
import com.agriweb.agripriceshop.domain.OrderStatus;
import com.agriweb.agripriceshop.dto.OrderDto;
import com.agriweb.agripriceshop.dto.OrderResponseDto;
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
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @PostMapping("/api/user/order")
    public ResponseEntity<String> order(@RequestBody OrderDto orderDto) {
        String loginId = SecurityUtil.getCurrentLoginId();
        Member member = memberService.findOnebyLoginId(loginId);
        Long memberId = member.getId();
        Long ordered = orderService.order(memberId, orderDto);
        return (ordered != null)? ResponseEntity.ok("주문이 완료되었습니다."):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문이 실패하였습니다.");
    }

    @Operation(summary = "주문 조회 메서드", description = "주문 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @GetMapping("/api/user/orders")
    public Page<OrderResponseDto> orderList(@RequestParam(name = "loginId",required = false) String  loginId,
                                            @RequestParam(name="orderStatus", required = false)OrderStatus orderStatus,
                                            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy


                                            ) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

            OrderSearch orderSearch = new OrderSearch();
            if (loginId != null) {
                orderSearch.setLoginId(loginId);
            }
            if (orderStatus != null) {
                orderSearch.setOrderStatus(orderStatus);
            }

            return orderService.findOrders(orderSearch, pageable);
    }
//    @GetMapping("/user/orders")
//    public List<Order> orderList(@RequestParam(name = "loginId",required = false) String  loginId,
//                                 @RequestParam(name="orderStatus", required = false)OrderStatus orderStatus) {
//        if (loginId == null && orderStatus == null) {
//            return null;
//        }
//        if (loginId != null && orderStatus == null) {
//            List<Order> orders = orderService.findAllOrders();
//            return orders;
//        } else if (loginId == null && orderStatus != null) {
//            List<Order> orders = orderService.findOrdersByOrderStatus(orderStatus);
//            return orders;
//        } else if (loginId != null && orderStatus != null) {
//            List<Order> orders = orderService.findOrdersByIdNStatus(loginId, orderStatus);
//            return orders;
//        }
//            return null;
//    }


    @Operation(summary = "주문 취소 메서드", description = "주문 취소 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/api/user/orders/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId ) {
        orderService.cancelOrder(orderId);
        Order canceled = orderService.findOne(orderId);
        return (canceled != null) ?
                ResponseEntity.ok(canceled.getId()+"번 주문이 취소되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문 취소가 실패하였습니다.");
        }

    @Operation(summary = "주문 완료 메서드", description = "주문 완료 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request operation")
    })
    @PostMapping("/api/user/orders/{orderId}/finish")
    public ResponseEntity<String> finishOrder(@PathVariable("orderId") Long orderId) {
        Long finished = orderService.finishOrder(orderId);
        return (finished != null) ?
                ResponseEntity.ok(finished + "번 주문이 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문 완료가 실패하였습니다.");

    }


}


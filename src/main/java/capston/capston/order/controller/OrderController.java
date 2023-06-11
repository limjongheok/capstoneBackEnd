package capston.capston.order.controller;

import capston.capston.locker.model.Locker;
import capston.capston.locker.service.LockerService;
import capston.capston.message.MessageService;
import capston.capston.order.dto.orderfindDTO.OrderResponseDTO;
import capston.capston.order.kakao.kakaoDTO.KakaoApproveResponseDTO;
import capston.capston.order.kakao.kakaoDTO.KakaoReadyResponseDTO;
import capston.capston.order.kakao.kakaoService.KakaoPayService;
import capston.capston.order.dto.OrderCreateDTO.OrderCreateResponseDTO;
import capston.capston.order.model.Order;
import capston.capston.order.service.OrderService;
import capston.capston.saleProduct.dto.saleProductFindmyDTO.SaleProductFindMyResponseDTO;
import capston.capston.saleProduct.model.SaleProduct;
import capston.capston.saleProduct.service.SaleProductService;
import capston.capston.user.dto.userInfoDTO.UserInfoResponseDTO;
import capston.capston.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final KakaoPayService kakaoPayService;
    private final OrderService orderService;
    private  final MessageService messageService;

    private  final LockerService lockerService;
    private final SaleProductService saleProductService;
    private  final UserService userService;
    // 주문 생성하기
    @GetMapping("/api/order/create/{productId}")
    public ResponseEntity<?> orderCreate(@PathVariable(value = "productId") long productId, Authentication authentication){

        SaleProduct product = saleProductService.findById(productId);

        // 사물함 배정
        Locker locker =lockerService.assignLocker(product,authentication); // 사물함 배정 받기
        // 주문 생성
        Order order = orderService.createOrder(productId, authentication);

        // 상품 주문 연관
        saleProductService.orderProduct(product,order,authentication);
        // 구매자 한테 보내기
        //messageService.sendAssignBuyerLocker(locker);

        // 판매자 한테 보내기
        messageService.sendAssignSaleLocker(locker);
        return ResponseEntity.ok().body(createResponse(OrderCreateResponseDTO.toOrderCreateResponseDTO(order),"상품 주문 생성 완료"));

    }





    // 카카오 주문 준비
    @GetMapping("/api/kakao/ready/{productId}")
    public ResponseEntity<?> readyToKakaoPay(@PathVariable (value = "productId") long productId) {
        KakaoReadyResponseDTO kakaoReadyResponseDTO = kakaoPayService.kakaoPayReady(productId);
        return ResponseEntity.ok().body(createResponse(kakaoReadyResponseDTO,"카카오페이 준비 완료"));
    }

    // 카카오 주문 성공

    // 카카오 주문 성공
    @GetMapping("/api/kakao/success/{productId}")
    public ResponseEntity afterPayRequest(@PathVariable(value = "productId") long productId,@RequestParam("pg_token") String pgToken) {

        System.out.println(pgToken);
        KakaoApproveResponseDTO kakaoApprove = kakaoPayService.approveResponse(productId,pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }


    // 내 판만 성공 조회

    @GetMapping("/api/order/sale")
    public  ResponseEntity<?> saleOrder(Authentication authentication){

        List<OrderResponseDTO> orderResponseDTOS = orderService.orderSaleResponseDTOS(authentication);
        return ResponseEntity.ok().body(createResponse(orderResponseDTOS,"판매 물품 성공 조회에 성공하였습니다."));


    }

    // 내 구매 조회
    @GetMapping("/api/order/buy")
    public ResponseEntity<?> buyOrder(Authentication authentication){
        List<OrderResponseDTO> orderResponseDTOS = orderService.orderBuyResponseDTOS(authentication);


        return ResponseEntity.ok().body(createResponse(orderResponseDTOS,"구매 물품 성공 조회에 성공하였습니다."));
    }

    @GetMapping("/api/myinfo")
    public ResponseEntity<?> myInfo(Authentication authentication){
        List<OrderResponseDTO> orderSaleResponseDTOS = orderService.orderSaleResponseDTOS(authentication); // 판매 성공 내역
        List<OrderResponseDTO> orderBuyResponseDTOSS = orderService.orderBuyResponseDTOS(authentication); // 내가 구매 성공 내역
        List<SaleProductFindMyResponseDTO> saleProductFindMyResponseDTOS = saleProductService.findMyProduct(authentication);//내가 올린 물건
        UserInfoResponseDTO userInfoResponseDTO = userService.userInfo(authentication);
        Map<String, Object> response = new HashMap<>();
        response.put("myinfo", userInfoResponseDTO);
        response.put("raised", saleProductFindMyResponseDTOS ); // 올린거
        response.put("sold",orderSaleResponseDTOS);
        response.put("purchase", orderBuyResponseDTOSS);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/api/redirect")
    public ResponseEntity<?> redirect() throws URISyntaxException {
        URI redirectUri  =new URI("http://loaclhost:80/");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return  new ResponseEntity<>(redirectUri, HttpStatus.OK);
    }
    private Map<String,Object> createResponse(Object object, String msg){
        Map<String, Object> response = new HashMap<>();
        response.put("msg", msg);
        response.put("data", object);
        return response;
    }


}

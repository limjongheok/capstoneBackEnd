package capston.capston.order.service;

import capston.capston.Error.CustomException;
import capston.capston.Error.ErrorCode;
import capston.capston.auth.PrincipalDetails;
import capston.capston.locker.dto.lockerAssignDTO.LockerAssignResponseDTO;
import capston.capston.locker.model.Locker;
import capston.capston.locker.service.LockerService;
import capston.capston.message.MessageService;
import capston.capston.order.dto.orderfindDTO.OrderResponseDTO;
import capston.capston.order.model.Order;
import capston.capston.order.dto.OrderCreateDTO.OrderCreateResponseDTO;
import capston.capston.order.repository.OrderRepository;
import capston.capston.saleProduct.model.SaleProduct;
import capston.capston.saleProduct.service.SaleProductService;
import capston.capston.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService implements OrderServiceQueryImpl, OrderServiceCommandImpl {
    private final OrderRepository orderRepository;
    private final SaleProductService productService;



    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
    @Override
    public List<Order> findSuccessSaleOrder(String studentId) {
        return orderRepository.findMySuccessOrder(studentId);
    }

    @Override
    public List<Order> findSuccessBuyOrder(String studentId) {
        return orderRepository.findMyBuySuccessOrder(studentId);
    }


    // 주문 생성시

    @Override
    public Order createOrder(long productId, Authentication authentication) {
        SaleProduct product = productService.findById(productId);
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser(); // 현재 로그인 유저
        if(product.isOrderStatus()){
            throw  new CustomException(ErrorCode.BadSuccessOrderException);
        }

        Order order = new Order(product,user);
        save(order);


        log.info(order.getSaleProduct().getSaleProductName());
        return order;
    }

    // user는 로그인 유저 판매 성공한 물품을 찾음

    @Override
    public List<OrderResponseDTO> orderSaleResponseDTOS(Authentication authentication) {
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser(); // 현재 로그인 유저
        List<Order> orders = findSuccessSaleOrder(user.getStudentId()); // 현재 로그인 유저 판매 성공 물품
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for(Order order : orders){
            orderResponseDTOS.add(OrderResponseDTO.toOrderSaleResponseDTO(order));
        }
        Collections.reverse(orderResponseDTOS);
        return orderResponseDTOS;
    }

    @Override
    public List<OrderResponseDTO> orderBuyResponseDTOS(Authentication authentication) {
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser(); // 현재 로그인 유저
        List<Order> orders = findSuccessBuyOrder(user.getStudentId());
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for(Order order : orders){
            System.out.println(order.getUser().getStudentId());
            orderResponseDTOS.add(OrderResponseDTO.toOrderBuyerResponseDTO(order));
        }
        Collections.reverse(orderResponseDTOS);
        return orderResponseDTOS;
    }


}

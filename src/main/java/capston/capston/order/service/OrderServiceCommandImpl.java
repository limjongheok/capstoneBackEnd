package capston.capston.order.service;

import capston.capston.order.dto.OrderCreateDTO.OrderCreateResponseDTO;
import capston.capston.order.dto.orderfindDTO.OrderResponseDTO;
import capston.capston.order.model.Order;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderServiceCommandImpl {

    Order createOrder(long productId, Authentication authentication);

    List<OrderResponseDTO> orderSaleResponseDTOS(Authentication authentication);
    List<OrderResponseDTO> orderBuyResponseDTOS(Authentication authentication);

}

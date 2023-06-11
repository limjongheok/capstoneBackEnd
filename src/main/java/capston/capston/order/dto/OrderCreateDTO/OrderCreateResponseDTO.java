package capston.capston.order.dto.OrderCreateDTO;

import capston.capston.order.model.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderCreateResponseDTO {
    private  long id;


    private String content;

    private String saleProductName;

    private LocalDateTime createDate;
    private  LocalDateTime modifiedDate;

    private String offerStudentId;

    @Builder

    private OrderCreateResponseDTO(long id, String content, String saleProductName, LocalDateTime createDate, LocalDateTime modifiedDate, String offerStudentId) {
        this.id = id;
        this.content = content;
        this.saleProductName = saleProductName;

        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.offerStudentId = offerStudentId;
    }

    public static OrderCreateResponseDTO toOrderCreateResponseDTO(Order order){
        return OrderCreateResponseDTO.builder()
                .id(order.getId())
                .content(order.getSaleProduct().getContent())
                .saleProductName(order.getSaleProduct().getSaleProductName())
                .createDate(order.getCreateDate())
                .modifiedDate(order.getModifiedDate())
                .build();

    }
}

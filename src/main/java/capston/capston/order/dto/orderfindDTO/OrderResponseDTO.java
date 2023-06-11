package capston.capston.order.dto.orderfindDTO;

import capston.capston.order.model.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.sound.midi.SysexMessage;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponseDTO {

    private  long id;


    private String content;

    private String saleProductName;

    private String imgUrl;
    private String studentId; // 판매자 아이디
    private LocalDateTime createDate;
    private  LocalDateTime modifiedDate;

    @Builder
    private OrderResponseDTO(long id, String content, String saleProductName, String imgUrl, String studentId, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.content = content;
        this.saleProductName = saleProductName;
        this.imgUrl = imgUrl;
        this.studentId = studentId;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }


    // 내가 올린 물건 , 구매자 를 알아야함
    public static OrderResponseDTO toOrderSaleResponseDTO(Order order){
        return  OrderResponseDTO.builder()
                .id(order.getId())
                .content(order.getSaleProduct().getContent())
                .saleProductName(order.getSaleProduct().getSaleProductName())
                .imgUrl(order.getSaleProduct().getImgUrl())
                .studentId(order.getUser().getStudentId())
                .createDate(order.getCreateDate())
                .modifiedDate(order.getModifiedDate())
                .build();

    }

    // 내가 구매한 물건 판매자를 알아야함
    public static  OrderResponseDTO toOrderBuyerResponseDTO(Order order){
        System.out.println(order.getSaleProduct().getUser().getStudentId());
        return  OrderResponseDTO.builder()
                .id(order.getId())
                .content(order.getSaleProduct().getContent())
                .saleProductName(order.getSaleProduct().getSaleProductName())
                .imgUrl(order.getSaleProduct().getImgUrl())
                .studentId(order.getSaleProduct().getUser().getStudentId())
                .createDate(order.getCreateDate())
                .modifiedDate(order.getModifiedDate())
                .build();
    }
}

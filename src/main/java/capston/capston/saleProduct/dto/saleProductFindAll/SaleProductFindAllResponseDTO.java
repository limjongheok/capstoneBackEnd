package capston.capston.saleProduct.dto.saleProductFindAll;

import capston.capston.saleProduct.dto.saleProductFindmyDTO.SaleProductFindMyResponseDTO;
import capston.capston.saleProduct.model.SaleProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class SaleProductFindAllResponseDTO {
    private  long id;

    private String content;

    private String saleProductName;

    private String imgUrl;

    private  long amount;
    private LocalDateTime createDate;
    private  LocalDateTime modifiedDate;

    @Builder
    private SaleProductFindAllResponseDTO(long id, String content, String saleProductName, String imgUrl,long amount, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.content = content;
        this.saleProductName = saleProductName;
        this.amount = amount;
        this.imgUrl = imgUrl;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public static SaleProductFindAllResponseDTO toSaleProductFindAllResponseDTO(SaleProduct saleProduct){
        return SaleProductFindAllResponseDTO.builder()
                .id(saleProduct.getId())
                .content(saleProduct.getContent())
                .saleProductName(saleProduct.getSaleProductName())
                .imgUrl(saleProduct.getImgUrl())
                .amount(saleProduct.getAmount())
                .createDate(saleProduct.getCreateDate())
                .modifiedDate(saleProduct.getModifiedDate())
                .build();
    }

}

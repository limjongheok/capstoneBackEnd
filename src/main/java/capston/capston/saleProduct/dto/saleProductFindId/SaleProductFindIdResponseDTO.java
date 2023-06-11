package capston.capston.saleProduct.dto.saleProductFindId;

import capston.capston.saleProduct.dto.saleProductFindmyDTO.SaleProductFindMyResponseDTO;
import capston.capston.saleProduct.model.SaleProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class SaleProductFindIdResponseDTO {
    private  long id;


    private String content;

    private String saleProductName;

    private String saleStudentId;

    private String imgUrl;

    private  long amount;
    private LocalDateTime createDate;
    private  LocalDateTime modifiedDate;

    @Builder
    private SaleProductFindIdResponseDTO(long id, String content, String saleProductName,String saleStudentId, String imgUrl,long amount, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.content = content;
        this.saleProductName = saleProductName;
        this.imgUrl = imgUrl;
        this.amount = amount;
        this.saleStudentId = saleStudentId;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public static SaleProductFindIdResponseDTO toSaleProductFindIdResponseDTO(SaleProduct saleProduct){
        return SaleProductFindIdResponseDTO.builder()
                .id(saleProduct.getId())
                .content(saleProduct.getContent())
                .saleProductName(saleProduct.getSaleProductName())
                .imgUrl(saleProduct.getImgUrl())
                .amount(saleProduct.getAmount())
                .saleStudentId(saleProduct.getUser().getStudentId())
                .createDate(saleProduct.getCreateDate())
                .modifiedDate(saleProduct.getModifiedDate())
                .build();
    }
}

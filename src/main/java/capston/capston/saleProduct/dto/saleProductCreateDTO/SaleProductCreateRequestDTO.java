package capston.capston.saleProduct.dto.saleProductCreateDTO;

import capston.capston.saleProduct.model.SaleProduct;
import capston.capston.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SaleProductCreateRequestDTO {


    @NotNull
    private String content;

    @NotNull
    private String saleProductName;

    @NotNull
    private String imgUrl;

    @NotNull
    private long amount;
    public SaleProduct toEntity(User user){
        return SaleProduct.builder()
                .saleProductName(saleProductName)
                .amount(this.amount)
                .content(this.content)
                .imgUrl(this.imgUrl)
                .user(user)
                .build();
    }



}

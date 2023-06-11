package capston.capston.saleProduct.repository;

import capston.capston.saleProduct.model.QSaleProduct;
import capston.capston.saleProduct.model.SaleProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public class SaleProductRepositoryImpl implements SaleProductRepositoryCustom {
    private  final JPAQueryFactory jpaQueryFactory;

    public SaleProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSaleProduct saleProduct = QSaleProduct.saleProduct;

    private BooleanExpression notOrderProduct(){
        return  saleProduct.orderStatus.isFalse();
    }
    private  BooleanExpression eqTitleAndName(String title){
        if(StringUtils.isEmpty(title)){
            return null;
        }
        return (saleProduct.saleProductName.contains(title).and(saleProduct.orderStatus.isFalse()));

    }


    @Override
    public List<SaleProduct> findNoneOrderProduct() {
        return (List<SaleProduct>) jpaQueryFactory.from(saleProduct).where(notOrderProduct()).fetch();
    }

    @Override
    public List<SaleProduct> getProductList(String title) {
        return (List<SaleProduct>) jpaQueryFactory.from(saleProduct).where(eqTitleAndName(title)).fetch();
    }


}

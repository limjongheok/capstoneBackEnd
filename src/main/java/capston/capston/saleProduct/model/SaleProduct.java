package capston.capston.saleProduct.model;


import capston.capston.jpaAuditing.BaseTimeEntity;
import capston.capston.order.model.Order;
import capston.capston.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
public class SaleProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="SaleProduct_ID")
    private  long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User user;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_ID")
    private Order order;



    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String saleProductName;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private String imgUrl;


    @Column(nullable = false)
    private boolean orderStatus; // 주문 생성 여부

    private  String buyStudentId;




    @Builder
    public SaleProduct(long id, User user, String content, String saleProductName, long amount, String imgUrl, String buyStudentId) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.saleProductName = saleProductName;
        this.amount = amount;
        this.imgUrl = imgUrl;
        this.orderStatus = false;
        this.buyStudentId = buyStudentId;
    }


    public void order(Order order, User user){
        orderProduct(order);
        orderStudentId(user);
        orderSuccess(); // 주문 생성 성공
    }

    private  void orderStudentId(User user){
        this.buyStudentId = user.getStudentId();
    }

    private void orderProduct(Order order){
        this.order = order;
    }

    private void orderSuccess(){
        this.orderStatus = true;
    }

}

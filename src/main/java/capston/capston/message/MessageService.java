package capston.capston.message;

import capston.capston.auth.PrincipalDetails;
import capston.capston.locker.model.Locker;
import capston.capston.locker.service.LockerService;
import capston.capston.saleProduct.model.SaleProduct;
import capston.capston.saleProduct.service.SaleProductService;
import capston.capston.user.model.User;
import capston.capston.user.service.UserService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private  final DefaultMessageService messageService;



    private MessageService(@Value("${message.apiKey}") String apiKey, @Value("${message.apiSecretKey}") String apiSecretKey, SaleProductService saleProductService, LockerService lockerService, UserService userService) {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 구매자 한테 사물함 지정 문자 보내기
//    public SingleMessageSentResponse sendAssignBuyerLocker( Locker locker){
//        User user = locker.getPurchasingUser();
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(createBuyerMessage(user,locker)));
//        return response;
//    }

    // 판매자 한테 사물함 지정 문자 보내기
    public SingleMessageSentResponse sendAssignSaleLocker(Locker locker){
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(createSellerMessage(locker)));
        return response;
    }

    // 물건 넣을시 구매자 한테 문자 보내기
    public SingleMessageSentResponse sendPutProductLocker(Locker locker){

        User user = locker.getPurchasingUser();
        Message message = new Message();
        message.setFrom("01029463517");
        message.setTo(user.getPhoneNumber()); // 구매자 폰 아이디
        message.setText(locker.getBuildingNum()+" "+"건물" +" " + locker.getId() +" 번 사물함에 주문하신 책이 보관되었습니다."+" 해당 사물함의 비밀번호는" + " "+ locker.getLockerPassword() + "입니다.");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return response;

    }

    // 물건 넣을시 구매자 한테 문자 보내기
    public SingleMessageSentResponse sendPushProductLocker(Locker locker){

        User sellUser = locker.getSaleProduct().getUser();
        Message message = new Message();
        message.setFrom("01029463517");
        message.setTo(sellUser.getPhoneNumber()); // 구매자 폰 아이디
        message.setText(locker.getBuildingNum()+" "+"건물" +" " + locker.getId() +" 번 사물함에 주문하신 책이 회수되었습니다.");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return response;

    }

    // 물건 구매시 판매자 한테 메시지 보내기
    private Message createSellerMessage(Locker locker){
        Message message = new Message();
        message.setFrom("01029463517");
        message.setTo(locker.getSaleProduct().getUser().getPhoneNumber()); // 구매자 폰 아이디
        message.setText("물건을 등록해 주세요" +locker.getBuildingNum()+" 건물 "+ locker.getId() +" 번 사물함에 배정 되었으며."  + " 해당 사물함의 비밀번호는" + " "+ locker.getLockerPassword() + "입니다.");
        return  message;
    }



}

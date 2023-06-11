package capston.capston.locker.service;

import capston.capston.locker.dto.lockerAssignDTO.LockerAssignResponseDTO;
import capston.capston.locker.dto.lockerCreateDTO.LockerCreateRequestDTO;
import capston.capston.locker.dto.lockerCreateDTO.LockerCreateResponseDTO;
import capston.capston.locker.dto.lockerPasswordCheckDTO.LockerPasswordCheckResponseDTO;
import capston.capston.locker.dto.lockerProdctDTO.LockerProductResponseDTO;
import capston.capston.locker.dto.lockerUnassignedDTO.LockerUnassignedRequestDTO;
import capston.capston.locker.dto.lockerUnassignedDTO.LockerUnassignedResponseDTO;
import capston.capston.locker.model.Locker;
import capston.capston.saleProduct.model.SaleProduct;
import capston.capston.user.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LockerServiceCommandImpl {

    LockerCreateResponseDTO createLocker(LockerCreateRequestDTO lockerCreateRequestDTO);

    List<LockerUnassignedResponseDTO> unassignedLocker(LockerUnassignedRequestDTO lockerUnassignedRequestDTO);
    Locker assignLocker(SaleProduct saleProduct, Authentication authentication);

    LockerPasswordCheckResponseDTO buyerCheckPassword(long lockerId, String lockerPassword);
    LockerPasswordCheckResponseDTO saleCheckPassword(long lockerId, String lockerPassword);
    LockerProductResponseDTO putProduct(Locker locker);
    LockerProductResponseDTO pushProduct(Locker locker);
}

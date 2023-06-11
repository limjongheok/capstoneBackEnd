package capston.capston.locker.repository;

import capston.capston.locker.dto.lockerUnassignedDTO.LockerUnassignedRequestDTO;
import capston.capston.locker.model.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long>, LockerRepositoryCustom {

    @Override
    List<Locker> getNotAssignLocker(int buildingNum);

    @Override
    List<Locker> ListNotAssignLocker();

}

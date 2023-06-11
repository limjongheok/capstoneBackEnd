package capston.capston.user.dto.userInfoDTO;

import capston.capston.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class UserInfoResponseDTO {
    private String email;
    private  String userName;
    private  String phoneNumber;
    private String studentId;
    private  long point;
    private LocalDateTime createDate;
    private  LocalDateTime modifiedDate;

    @Builder
    private UserInfoResponseDTO(String email, String userName, String phoneNumber,long point,String studentId, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.email = email;
        this.userName = userName;
        this.point = point;
        this.studentId = studentId;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
    public static UserInfoResponseDTO toUserInfoResponseDTO(User user){
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .studentId(user.getStudentId())
                .phoneNumber(user.getPhoneNumber())
                .point(user.getPoint())
                .createDate(user.getCreateDate())
                .modifiedDate(user.getModifiedDate())
                .build();

    }
}

package jeje.work.aeatbe.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String userId;
    private String allergies;
    private String freeFrom;
    private String userName;
    private String userImgUrl;

    public UserDTO(Long id, String userName, String userImgUrl) {
        this.id = id;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
    }

    public UserDTO(Long id, String userId, String allergies, String freeFrom) {
        this.id = id;
        this.userId = userId;
        this.allergies = allergies;
        this.freeFrom = freeFrom;
    }

}
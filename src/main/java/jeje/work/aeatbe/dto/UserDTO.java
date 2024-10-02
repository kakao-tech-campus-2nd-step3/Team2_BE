package jeje.work.aeatbe.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;
    private String userId;
    private String allergies;
    private String freeFrom;

}
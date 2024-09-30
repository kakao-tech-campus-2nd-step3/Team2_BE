package jeje.work.aeatbe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WishlistDTO {

    private int id;
    private int userId;
    private int productId;

}
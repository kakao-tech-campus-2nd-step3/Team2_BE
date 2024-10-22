package jeje.work.aeatbe.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String allergens;
    private String nutritionalInfo;
    private String productImageUrl;
    private String metaImageUrl;
    private String typeName;
    private String manufacturer;
    private String seller;
    private String capacity;
    private String productName;
    private String ingredients;
    private int price;
}
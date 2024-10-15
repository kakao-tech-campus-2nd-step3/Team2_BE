package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String allergens;

    @Lob
    private String nutritionalInfo;

    @Column(name = "product_image_url", length = 255)
    private String productImageUrl;

    @Column(name = "meta_image_url", length = 255)
    private String metaImageUrl;

    @Column(name = "type_name", length = 200)
    private String typeName;

    @Column(length = 50)
    private String manufacturer;

    @Column(length = 50)
    private String seller;

    @Column(length = 20)
    private String capacity;

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    @Lob
    private String ingredients;

}
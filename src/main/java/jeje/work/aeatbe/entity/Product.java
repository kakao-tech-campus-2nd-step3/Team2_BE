package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_name", columnList = "product_name"),
                @Index(name = "idx_price", columnList = "price")
        },
    uniqueConstraints = {
    @UniqueConstraint(name = "unique_product_name_manufacturer", columnNames = {"product_name", "manufacturer"})
}

)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String nutritionalInfo;

    @Column(name = "product_image_url", columnDefinition = "TEXT")
    private String productImageUrl;

    @Column(name = "meta_image_url", columnDefinition = "TEXT")
    private String metaImageUrl;

    @Column(name = "type_name", length = 200)
    private String typeName;

    @Column(length = 200)
    private String manufacturer;

    @Column(columnDefinition = "TEXT")
    private String seller;

    @Column(length = 20)
    private String mallName;

    @Column(length = 100)
    private String capacity;

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    @Lob
    private String ingredients;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 99990000")
    private Long price = 99990000L;

    @Column(name = "promotion_tag")
    private String tag;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAllergy> productAllergies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFreeFrom> productFreeFroms = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.price = 99990000L;
    }


    public void updateField(String mallName, Long price, String seller, String productImageUrl) {
        this.mallName = mallName;
        this.price = price;
        this.seller = seller;
        this.productImageUrl = productImageUrl;
    }

    public void updateTag(String tag) {
        this.tag = tag;
    }

}
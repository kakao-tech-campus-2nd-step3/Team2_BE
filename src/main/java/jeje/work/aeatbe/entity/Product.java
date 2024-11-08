package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false, columnDefinition = "DEFAULT 0")
    private Long price;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAllergy> productAllergies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFreeFrom> productFreeFroms = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
package jeje.work.aeatbe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public Product() {
    }

    public Product(
            int id,
            String allergens,
            String nutritionalInfo,
            String productImageUrl,
            String metaImageUrl,
            String typeName,
            String manufacturer,
            String seller,
            String capacity,
            String productName,
            String ingredients) {
        this.id = id;
        this.allergens = allergens;
        this.nutritionalInfo = nutritionalInfo;
        this.productImageUrl = productImageUrl;
        this.metaImageUrl = metaImageUrl;
        this.typeName = typeName;
        this.manufacturer = manufacturer;
        this.seller = seller;
        this.capacity = capacity;
        this.productName = productName;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getAllergens() {
        return allergens;
    }

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getMetaImageUrl() {
        return metaImageUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getSeller() {
        return seller;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getProductName() {
        return productName;
    }

    public String getIngredients() {
        return ingredients;
    }

}
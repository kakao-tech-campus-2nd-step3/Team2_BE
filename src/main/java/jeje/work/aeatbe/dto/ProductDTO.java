package jeje.work.aeatbe.dto;

public class ProductDTO {
    private int id;
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

    public ProductDTO() {
    }

    public ProductDTO(
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

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(String nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getMetaImageUrl() {
        return metaImageUrl;
    }

    public void setMetaImageUrl(String metaImageUrl) {
        this.metaImageUrl = metaImageUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
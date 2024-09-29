package jeje.work.aeatbe.dto;

public class AllergyCategoryDTO {
    private int id;
    private String allergyType;

    public AllergyCategoryDTO() {
    }

    public AllergyCategoryDTO(int id, String allergyType) {
        this.id = id;
        this.allergyType = allergyType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }
}
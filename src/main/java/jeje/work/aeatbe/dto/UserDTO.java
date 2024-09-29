package jeje.work.aeatbe.dto;

public class UserDTO {
    private int id;
    private String userId;
    private String allergies;
    private String freeFrom;

    public UserDTO() {
    }

    public UserDTO(int id, String userId, String allergies, String freeFrom) {
        this.id = id;
        this.userId = userId;
        this.allergies = allergies;
        this.freeFrom = freeFrom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getFreeFrom() {
        return freeFrom;
    }

    public void setFreeFrom(String freeFrom) {
        this.freeFrom = freeFrom;
    }
}
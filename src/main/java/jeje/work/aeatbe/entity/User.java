package jeje.work.aeatbe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false, unique = true, length = 100)
    private String userId;

    @Column(length = 100)
    private String allergies;

    @Column(name = "free_from", length = 100)
    private String freeFrom;

    public User() {
    }

    public User(int id, String userId, String allergies, String freeFrom) {
        this.id = id;
        this.userId = userId;
        this.allergies = allergies;
        this.freeFrom = freeFrom;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getFreeFrom() {
        return freeFrom;
    }

}
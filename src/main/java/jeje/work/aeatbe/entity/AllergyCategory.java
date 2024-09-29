package jeje.work.aeatbe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "allergy_categories")
public class AllergyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "allergy_type", nullable = false, length = 25)
    private String allergyType;

    public AllergyCategory() {
    }

    public AllergyCategory(int id, String allergyType) {
        this.id = id;
        this.allergyType = allergyType;
    }

    public int getId() {
        return id;
    }

    public String getAllergyType() {
        return allergyType;
    }

}
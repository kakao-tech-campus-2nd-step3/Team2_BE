package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wishlists")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
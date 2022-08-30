package com.example.javaproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "clothes")
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clothing_name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "clothing_category")
    @Enumerated(EnumType.STRING)
    private ClothingCategory clothingCategory;

    @Column(name = "size")
    private String size;

    @Column(name = "clothing_details")
    private String clothingDetails;

    @ManyToMany(mappedBy = "clothes")
    private List<ShoppingCart> shoppingCarts;

    @ManyToMany(mappedBy = "clothes")
    private List<Order> orders;

    @OneToMany(mappedBy="clothing")
    private List<Review> reviews;
}

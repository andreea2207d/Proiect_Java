package com.example.javaproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinTable(
            name = "clothing_shopping_cart",
            joinColumns = @JoinColumn(name = "fk_shopping_cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_clothing_id", referencedColumnName = "id"))

    private List<Clothing> clothes = new ArrayList<>();

    @OneToOne(mappedBy = "shoppingCart")
    private User user;

    @Transient
    private Double total;

    public void totalAmount(){

        double x = 0;

        for (Clothing clothes: clothes){
            x += clothes.getPrice();
        }

        total = x;
    }

}

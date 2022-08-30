package com.example.javaproject.repository;

import com.example.javaproject.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findAll();

    @Query("select s from ShoppingCart s join s.user u where u.id = :userId")
    ShoppingCart findShoppingCartByUserId(Long userId);
}

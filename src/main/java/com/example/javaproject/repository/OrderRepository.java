package com.example.javaproject.repository;

import com.example.javaproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();

    @Query("select o from Order o join o.user u where u.id = :userId")
    List<Order> findOrdersByUserId(Long userId);

}

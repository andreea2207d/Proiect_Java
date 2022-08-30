package com.example.javaproject.controller;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.exception.ShoppingCartNotFound;
import com.example.javaproject.exception.OrderNotFound;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.model.OrderStatus;
import com.example.javaproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/new/{userId}")
    public ResponseEntity<OrderDto> placeNewOrder(@PathVariable Long userId) throws ShoppingCartNotFound, ClothingNotFound {
        return ResponseEntity
                .ok()
                .body(orderService.placeNewOrder(userId));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<OrderDto>> findAllOrders() {
        return ResponseEntity
                .ok()
                .body(orderService.findAllOrders());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> findOrdersByUser(@PathVariable Long userId) throws OrderNotFound {
        return ResponseEntity
                .ok()
                .body(orderService.findOrdersByUser(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable Long orderId) throws OrderNotFound {
        return ResponseEntity
                .ok()
                .body(orderService.findOrderById(orderId));
    }

    @PutMapping("/updateStatus/{id}/{orderStatus}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @PathVariable OrderStatus orderStatus) throws OrderNotFound {
        return ResponseEntity
                .ok()
                .body(orderService.updateOrder(orderStatus, id));
    }

}

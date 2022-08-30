package com.example.javaproject.service;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.exception.ShoppingCartNotFound;
import com.example.javaproject.exception.OrderNotFound;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.mapper.OrderMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    private Order newOrder(User user, ShoppingCart shoppingCart) {
        Order order = new Order();

        order.setUser(user);
        order.setClothes(shoppingCart.getClothes());
        order.setOrderStatus(OrderStatus.SENT);

        return order;
    }

    @Transactional
    public OrderDto placeNewOrder(Long userId) throws ShoppingCartNotFound, ClothingNotFound {
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = shoppingCartService.findById(user.getShoppingCart().getId());
        Order order = newOrder(user, shoppingCart);
        shoppingCartService.deleteShoppingCartByUserId(userId);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapToDto(savedOrder);
    }

    public List<OrderDto> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List <OrderDto> ordersDto = orders.stream().map(order -> orderMapper.mapToDto(order))
                .collect(Collectors.toList());
        return ordersDto;
    }

    public OrderDto findOrderById(Long id) throws OrderNotFound {
        Optional<Order> order =  orderRepository.findById(id);
        if (order.isEmpty())
            throw new OrderNotFound("Order not found!");
        return orderMapper.mapToDto(order.get());
    }

    public List<OrderDto> findOrdersByUser(Long userId) throws OrderNotFound {
        List<Order> orders =  orderRepository.findOrdersByUserId(userId);
        if (orders == null)
            throw new OrderNotFound("Orders not found!");
        List <OrderDto> ordersDto = orders.stream().map(order -> orderMapper.mapToDto(order))
                .collect(Collectors.toList());
        return ordersDto;
    }

    public Order findById(Long id) throws OrderNotFound {
        Optional<Order> order =  orderRepository.findById(id);
        if (order.isEmpty())
            throw new OrderNotFound("Order not found!");
        return order.get();
    }

    public OrderDto updateOrder(OrderStatus orderStatus, Long id) throws OrderNotFound {
        Order order = findById(id);
        order.setOrderStatus(orderStatus);
        return orderMapper.mapToDto(orderRepository.save(order));
    }

}

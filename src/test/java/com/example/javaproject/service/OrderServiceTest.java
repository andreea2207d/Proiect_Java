package com.example.javaproject.service;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.exception.*;
import com.example.javaproject.mapper.OrderMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private static final Long ORDER_ID = 1L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.SENT;
    private static final Long USER_ID = 1L;
    private static final String FIRST_NAME = "Bianca";
    private static final String LAST_NAME = "Dinu";
    private static final String EMAIL = "bianca@gmail.com";
    private static final Long SHOPPING_CART_ID = 1L;
    private static final Long CLOTHING_ID = 1L;
    private static final String NAME = "Rochie";
    private static final String SIZE = "S";
    private static final Double PRICE = 89.99;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private OrderService orderService;

    private User User() {
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL);

        return user;
    }

    private Clothing Clothing() {
        Clothing clothing = new Clothing();
        clothing.setId(CLOTHING_ID);
        clothing.setName(NAME);
        clothing.setPrice(PRICE);
        clothing.setSize(SIZE);

        return clothing;
    }

    private ShoppingCart shoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(SHOPPING_CART_ID);
        User user = User();
        Clothing clothing = Clothing();
        shoppingCart.setUser(user);
        shoppingCart.setClothes(List.of(clothing));

        return shoppingCart;
    }

    private OrderDto OrderDto() {
        return OrderDto.builder()
                .id(ORDER_ID)
                .build();
    }

    private Order Order() {
        Order order = new Order();
        order.setOrderStatus(ORDER_STATUS);
        User user = User();
        Clothing clothing = Clothing();
        order.setUser(user);
        order.setClothes(List.of(clothing));

        return order;
    }

    @Test
    @DisplayName("Place an order successfully")
    void placeOrder() throws ShoppingCartNotFound, ClothingNotFound {
        User user = User();
        ShoppingCart shoppingCart = shoppingCart();
        user.setShoppingCart(shoppingCart);

        Clothing clothing = Clothing();
        clothing.setId(CLOTHING_ID);
        clothing.setName(NAME);
        clothing.setPrice(PRICE);
        clothing.setSize(SIZE);
        List<Clothing> clothes = List.of(clothing);
        shoppingCart.setClothes(clothes);

        OrderDto orderDto = OrderDto();
        Order savedOrder = Order();

        when(userService.findById(USER_ID)).thenReturn(user);
        when(shoppingCartService.findById(SHOPPING_CART_ID)).thenReturn(shoppingCart);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.mapToDto(savedOrder)).thenReturn(orderDto);

        OrderDto result = orderService.placeNewOrder(USER_ID);

        assertNotNull(result);
        assertEquals(orderDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Find all orders successfully")
    void findAllOrders() {
        List<Order> orders = List.of(Order());
        List<OrderDto> ordersDto =  List.of(OrderDto());

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.mapToDto(orders.get(0))).thenReturn(ordersDto.get(0));

        List<OrderDto> result = orderService.findAllOrders();

        assertNotNull(result);
        assertEquals(ordersDto.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("Find an order by id successfully")
    void findOrderById() throws OrderNotFound {
        Order order = Order();
        order.setId(ORDER_ID);
        Optional<Order> optionalOrder = Optional.of(order);
        OrderDto orderDto = OrderDto();

        when(orderRepository.findById(ORDER_ID)).thenReturn(optionalOrder);
        when(orderMapper.mapToDto(optionalOrder.get())).thenReturn(orderDto);

        OrderDto result = orderService.findOrderById(ORDER_ID);

        assertNotNull(result);
        assertEquals(orderDto.getId(), result.getId());
        assertEquals(orderDto.getOrderStatus(), result.getOrderStatus());
    }

    @Test
    @DisplayName("Update an order successfully")
    void updateOrder() throws ClothingNotFound, OrderNotFound {
        Order order = Order();
        Order findOrder = Order();
        Optional<Order> optionalOrder = Optional.of(findOrder);

        Order savedOrder = Order();
        savedOrder.setId(ORDER_ID);

        OrderDto returnedOrderDTO = OrderDto();
        returnedOrderDTO.setId(ORDER_ID);

        when(orderRepository.findById(ORDER_ID)).thenReturn(optionalOrder);
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(orderMapper.mapToDto(savedOrder)).thenReturn(returnedOrderDTO);

        OrderDto result = orderService.updateOrder(ORDER_STATUS, ORDER_ID);

        assertNotNull(result);
        assertEquals(returnedOrderDTO.getId(), result.getId());
        assertEquals(returnedOrderDTO.getOrderStatus(), result.getOrderStatus());
    }

}
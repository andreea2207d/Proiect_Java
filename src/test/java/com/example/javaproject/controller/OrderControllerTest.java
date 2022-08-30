package com.example.javaproject.controller;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.model.OrderStatus;
import com.example.javaproject.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    private static final Long ID = 1L;
    private static final Long USER_ID = 1L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.SENT;

    private OrderDto OrderDto() {
        return OrderDto.builder()
                .id(ID)
                .userId(USER_ID)
                .orderStatus(String.valueOf(ORDER_STATUS))
                .build();
    }

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test - place an order")
    void testPlaceOrder() throws Exception {
        OrderDto dto = OrderDto();
        Long userId = 1L;
        when(orderService.placeNewOrder(any())).thenReturn(dto);

        mockMvc.perform(post("/shop/order/new/" + userId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test - find all orders")
    void testFindAllOrders() throws Exception {
        List<OrderDto> dto = List.of(OrderDto());
        when(orderService.findAllOrders()).thenReturn(dto);

        mockMvc.perform(get("/shop/order/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test - find an order")
    void testFindOrder() throws Exception {
        OrderDto dto = OrderDto();
        Long id = 1L;
        when(orderService.findOrderById(any())).thenReturn(dto);

        mockMvc.perform(get("/shop/order/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test - find orders by user id")
    void testFindOrdersByUser() throws Exception {
        List<OrderDto> dto = List.of(OrderDto());
        Long userId = 1L;
        when(orderService.findOrdersByUser(any())).thenReturn(dto);

        mockMvc.perform(get("/shop/order/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test - update status of an order")
    void testUpdateOrder() throws Exception {
        OrderDto dto = OrderDto();
        Long id = 1L;
        OrderStatus orderStatus = OrderStatus.PROCESSING;
        when(orderService.updateOrder(any(), any())).thenReturn(dto);

        mockMvc.perform(put("/shop/order/updateStatus/" + id + "/" + orderStatus))
                .andExpect(status().isOk())
                .andReturn();
    }

}
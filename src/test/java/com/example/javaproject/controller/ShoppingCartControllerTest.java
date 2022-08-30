package com.example.javaproject.controller;

import com.example.javaproject.dto.ShoppingCartDto;
import com.example.javaproject.service.ShoppingCartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ShoppingCartController.class)
class ShoppingCartControllerTest {
    private static final Long ID = 1L;
    private static final long USER_ID = 1;

    private ShoppingCartDto ShoppingCartDto() {
        return ShoppingCartDto.builder()
                .id(ID)
                .userId(USER_ID)
                .build();
    }

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test - add a clothing to shopping cart")
    void testAddClothingToCart() throws Exception {
        ShoppingCartDto dto = ShoppingCartDto();
        Long userId = 1L;
        Long clothingId = 1L;
        when(shoppingCartService.addClothingToShoppingCart(any(), any())).thenReturn(dto);

        mockMvc.perform(post("/shop/shoppingCart/add/" + userId + "/" + clothingId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test - find shopping cart by user id")
    void testFindShoppingCart() throws Exception {
        ShoppingCartDto dto = ShoppingCartDto();
        Long userId = 1L;
        when(shoppingCartService.findShoppingCartByUser(any())).thenReturn(dto);

        mockMvc.perform(get("/shop/shoppingCart/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test - delete a clothing from shopping cart")
    void testDeleteClothingFromCart() throws Exception {
        Long id = 1L;
        Long clothingId = 1L;

        mockMvc.perform(put("/shop/shoppingCart/delete/" + id + "/" + clothingId))
                .andExpect(status().isOk())
                .andReturn();
    }

}
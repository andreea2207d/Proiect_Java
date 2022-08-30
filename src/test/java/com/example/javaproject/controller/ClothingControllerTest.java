package com.example.javaproject.controller;

import com.example.javaproject.dto.ClothingDto;
import com.example.javaproject.mapper.ClothingMapper;
import com.example.javaproject.model.ClothingCategory;
import com.example.javaproject.service.ClothingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClothingController.class)
class ClothingControllerTest {
    private static final Long ID = 1L;
    private static final ClothingCategory CLOTHING_CATEGORY = ClothingCategory.Dresses;
    private static final Double PRICE = 89.99;
    private static final String NAME = "Rochie de vara";
    private static final String SIZE = "M-36";
    private static final String CLOTHING_DETAILS = "Culoare: roz";

    private ClothingDto ClothingDto() {
        return ClothingDto.builder()
                .id(ID)
                .name(NAME)
                .price(PRICE)
                .clothingCategory(CLOTHING_CATEGORY)
                .size(SIZE)
                .clothingDetails(CLOTHING_DETAILS)
                .build();
    }

    @MockBean
    private ClothingService clothingService;

    @MockBean
    private ClothingMapper clothingMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test - add a clothing")
    void testAddClothing() throws Exception {
        //Arrange
        ClothingDto dto = ClothingDto();
        ClothingCategory clothingCategory = ClothingCategory.Dresses;
        when(clothingService.addClothing(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/shop/clothes/new/" + clothingCategory)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Test - find all clothes")
    void testFindAllClothes() throws Exception {
        List<ClothingDto> dto = List.of(ClothingDto());
        when(clothingService.findAllClothes()).thenReturn(dto);

        mockMvc.perform(get("/shop/clothes/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(dto.get(0).getName())))
                .andExpect(jsonPath("$[0].price", is(dto.get(0).getPrice())))
                .andExpect(jsonPath("$[0].size", is(dto.get(0).getSize())))
                .andExpect(jsonPath("$[0].clothingDetails", is(dto.get(0).getClothingDetails())));
    }

    @Test
    @DisplayName("Test - find a clothing")
    void testFindClothing() throws Exception {
        ClothingDto dto = ClothingDto();
        Long id = 1L;
        when(clothingService.findClothingById(any())).thenReturn(dto);

        mockMvc.perform(get("/shop/clothes/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.price", is(dto.getPrice())))
                .andExpect(jsonPath("$.size", is(dto.getSize())))
                .andExpect(jsonPath("$.clothingDetails", is(dto.getClothingDetails())));
    }

    @Test
    @DisplayName("Test - find all clothes by category")
    void testFindClothesByCategory() throws Exception {
        List<ClothingDto> dto = List.of(ClothingDto());
        ClothingCategory clothingCategory = ClothingCategory.Dresses;
        when(clothingService.findClothesByCategory(clothingCategory)).thenReturn(dto);

        mockMvc.perform(get("/shop/clothes/findAll/" + clothingCategory))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(dto.get(0).getName())))
                .andExpect(jsonPath("$[0].price", is(dto.get(0).getPrice())))
                .andExpect(jsonPath("$[0].size", is(dto.get(0).getSize())))
                .andExpect(jsonPath("$[0].clothingDetails", is(dto.get(0).getClothingDetails())));
    }

    @Test
    @DisplayName("Test - update a clothing")
    void testUpdateClothing() throws Exception {
        //Arrange
        ClothingDto dto = ClothingDto();
        Long id = 1L;
        ClothingCategory clothingCategory = ClothingCategory.Pants;
        when(clothingService.updateClothing(any(), any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/shop/clothes/update/" + id)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Test - delete a clothing")
    void testDeleteClothing() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/shop/clothes/delete/" + id))
                .andExpect(status().isNoContent());
    }

}
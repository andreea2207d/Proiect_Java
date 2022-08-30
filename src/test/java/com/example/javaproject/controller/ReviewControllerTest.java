package com.example.javaproject.controller;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.service.ReviewService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {
    private static final Long ID = 1L;
    private static final String REVIEW = "Un produs conform asteptarilor";
    private static final long USER_ID = 1;
    private static final long CLOTHING_ID = 1;

    private ReviewDto ReviewDto() {
        return ReviewDto.builder()
                .id(ID)
                .review(REVIEW)
                .userId(USER_ID)
                .clothingId(CLOTHING_ID)
                .build();
    }

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewMapper reviewMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test - add a review")
    void testAddReview() throws Exception {
        //Arrange
        ReviewDto dto = ReviewDto();
        when(reviewService.addReview(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/shop/review/add/")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Test - find all reviews from a clothing")
    void testFindReviewsByClothing() throws Exception {
        List<ReviewDto> dto = List.of(ReviewDto());
        Long id = 1L;
        when(reviewService.findReviewsByClothing(any())).thenReturn(dto);

        mockMvc.perform(get("/shop/review/findAll/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].review", is(dto.get(0).getReview())));
    }

    @Test
    @DisplayName("Test - delete a review")
    void testDeleteReview() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/shop/review/delete/" + id))
                .andExpect(status().isNoContent());
    }

}
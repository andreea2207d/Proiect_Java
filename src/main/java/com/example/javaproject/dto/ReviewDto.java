package com.example.javaproject.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class ReviewDto {

    private Long id;

    @NotBlank(message = "The review can not be empty!")
    private String review;

    @NotNull(message = "ClothingId is mandatory!")
    private Long clothingId;

    @NotNull(message = "UserId is mandatory!")
    private Long userId;

}

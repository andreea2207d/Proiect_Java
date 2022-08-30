package com.example.javaproject.dto;

import com.example.javaproject.model.ClothingCategory;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class ClothingDto {

    private Long id;

    @NotEmpty
    @NotNull(message = "Name is mandatory!")
    private String name;

    @NotNull(message = "Price is mandatory!")
    private Double price;

    private ClothingCategory clothingCategory;

    @NotNull(message = "Size is mandatory!")
    private String size;

    private String clothingDetails;

}

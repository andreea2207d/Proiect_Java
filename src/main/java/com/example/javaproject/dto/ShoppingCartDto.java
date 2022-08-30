package com.example.javaproject.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
public class ShoppingCartDto {

    private Long id;

    @NotNull(message = "UserId is mandatory!")
    private Long userId;

    private List<ClothingDto> clothes;

    private Double total;

}

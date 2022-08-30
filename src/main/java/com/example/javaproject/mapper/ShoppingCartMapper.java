package com.example.javaproject.mapper;

import com.example.javaproject.dto.ShoppingCartDto;
import com.example.javaproject.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ClothingMapper.class)
public interface ShoppingCartMapper {

    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto mapToDto(ShoppingCart shoppingCart);

    @Mapping(target = "user.id", source = "userId")
    ShoppingCart mapToEntity(ShoppingCartDto shoppingCartDto);
}

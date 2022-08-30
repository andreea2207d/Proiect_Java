package com.example.javaproject.mapper;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ClothingMapper.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderDto mapToDto(Order order);

    @Mapping(target = "user.id", source = "userId")
    Order mapToEntity(OrderDto orderDto);
}

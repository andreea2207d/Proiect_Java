package com.example.javaproject.mapper;

import com.example.javaproject.dto.ClothingDto;
import com.example.javaproject.model.Clothing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClothingMapper {

    ClothingDto mapToDto(Clothing clothing);
    Clothing mapToEntity(ClothingDto clothingDTO);

}

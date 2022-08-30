package com.example.javaproject.mapper;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "clothingId", source = "clothing.id")
    ReviewDto mapToDto(Review review);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "clothing.id", source = "clothingId")
    Review mapToEntity(ReviewDto reviewDto);
}

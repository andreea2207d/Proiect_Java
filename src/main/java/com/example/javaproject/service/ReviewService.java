package com.example.javaproject.service;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.exception.ReviewNotFound;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClothingService clothingService;

    public ReviewDto addReview(ReviewDto reviewDto) throws ClothingNotFound {
        Review review = reviewMapper.mapToEntity(reviewDto);
        User user = userService.findById(review.getUser().getId());
        Clothing clothing = clothingService.findById(review.getClothing().getId());

        review.setUser(user);
        review.setClothing(clothing);

        return reviewMapper.mapToDto(reviewRepository.save(review));
    }

    public List<ReviewDto> findReviewsByClothing(Long clothingId) throws ReviewNotFound {
        List<Review> reviews =  reviewRepository.findReviewsByClothingId(clothingId);
        if (reviews == null)
            throw new ReviewNotFound("Reviews not found!");
        List <ReviewDto> reviewDtos = reviews.stream().map(review -> reviewMapper.mapToDto(review))
                .collect(Collectors.toList());
        return reviewDtos;
    }

    public Review findById(Long id) throws ReviewNotFound {
        Optional<Review> review =  reviewRepository.findById(id);
        if (review.isEmpty())
            throw new ReviewNotFound("Review not found!");
        return review.get();
    }

    public void deleteReviewById(Long id) throws ReviewNotFound {
        Review review = findById(id);
        reviewRepository.delete(review);
    }
}

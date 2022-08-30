package com.example.javaproject.controller;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.exception.ReviewNotFound;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shop/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMapper reviewMapper;

    @PostMapping("/add")
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewDto reviewDto) throws ClothingNotFound {
        return ResponseEntity
                .ok()
                .body(reviewService.addReview(reviewDto));
    }

    @GetMapping("/findAll/{clothingId}")
    public ResponseEntity<List<ReviewDto>> findReviewsByClothingId(@PathVariable Long clothingId) throws ReviewNotFound {
        return ResponseEntity
                .ok()
                .body(reviewService.findReviewsByClothing(clothingId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable Long id) throws ReviewNotFound {
        reviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.javaproject.service;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.exception.ReviewNotFound;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.model.Clothing;
import com.example.javaproject.model.Review;
import com.example.javaproject.model.User;
import com.example.javaproject.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    private static final Long ID = 1L;
    private static final String REVIEW = "Imi vine perfect aceasta rochie";
    private static final String EMAIL = "andreea@gmail.com";
    private static final String NAME = "Rochie";

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private ClothingService clothingService;

    @InjectMocks
    private ReviewService reviewService;

    private User User() {
        User user = new User();
        user.setEmail(EMAIL);

        return user;
    }

    private Clothing Clothing() {
        Clothing clothing = new Clothing();
        clothing.setName(NAME);

        return clothing;
    }

    private Review Review() {
        Review review = new Review();
        review.setReview(REVIEW);
        User user = User();
        Clothing clothing = Clothing();
        review.setUser(user);
        review.setClothing(clothing);

        return review;
    }

    private ReviewDto ReviewDto() {
        return ReviewDto.builder()
                .review(REVIEW)
                .build();
    }

    @Test
    @DisplayName("Add a review successfully")
    void addReview() throws ClothingNotFound {
        ReviewDto reviewDto = ReviewDto();
        Review review = Review();

        Review savedReview = Review();
        savedReview.setId(ID);

        ReviewDto returnedReviewDto = ReviewDto();
        returnedReviewDto.setId(ID);

        when(reviewMapper.mapToEntity(reviewDto)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(savedReview);
        when(reviewMapper.mapToDto(savedReview)).thenReturn(returnedReviewDto);

        ReviewDto result = reviewService.addReview(reviewDto);

        assertNotNull(result);
        assertEquals(returnedReviewDto.getReview(), result.getReview());
        assertEquals(returnedReviewDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Find reviews by clothing successfully")
    void findAllReviewsByClothing() throws ReviewNotFound {
        List<Review> reviews = List.of(Review());
        List<ReviewDto> reviewsDto =  List.of(ReviewDto());

        when(reviewRepository.findReviewsByClothingId(ID)).thenReturn(reviews);
        when(reviewMapper.mapToDto(reviews.get(0))).thenReturn(reviewsDto.get(0));

        List<ReviewDto> result = reviewService.findReviewsByClothing(ID);

        assertNotNull(result);
        assertEquals(reviewsDto.get(0).getReview(), result.get(0).getReview());
        assertEquals(reviewsDto.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("Delete a review successfully")
    void deleteReview() throws ReviewNotFound {
        Review review = Review();
        review.setId(ID);
        Optional<Review> optionalReview = Optional.of(review);

        when(reviewRepository.findById(ID)).thenReturn(optionalReview);

        reviewService.deleteReviewById(ID);

        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    @DisplayName("Delete a review fails")
    void deleteReviewNotFound()  {
        when(reviewRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFound.class, () -> reviewService.deleteReviewById(ID));
    }

}
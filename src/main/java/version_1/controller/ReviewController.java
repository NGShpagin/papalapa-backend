package version_1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import version_1.dto.ResponseMessageDto;
import version_1.dto.product.UpdateReviewDto;
import version_1.dto.review.NewReviewDto;
import version_1.dto.review.ReviewDto;
import version_1.model.Review;
import version_1.repository.ReviewRepository;
import version_1.service.ReviewService;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping(path = "/papalapa/reviews")
@Tag(name = "Review")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    private final ModelMapper modelMapper = new ModelMapper();

    @CrossOrigin
    @GetMapping(path = "/wb")
    public ResponseEntity<?> getReviewsFromWb() {
        List<Review> reviewList = reviewService.getReviewsFromWb();
        List<ReviewDto> reviewDtoList = reviewList
                .stream()
                .map(review -> {
                    ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
                    reviewDto.setProductName(review.getProduct().getCategory().getTitle());
                    return reviewDto;
                })
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(reviewDtoList);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getReviews(@RequestParam(value = "page", required = false, defaultValue = "1") @Min(1) Integer page,
                                        @RequestParam(value = "limit", required = false, defaultValue = "3") @Min(1) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewsFromDb(page, limit));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReviewById(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> addReview(@Valid @RequestBody NewReviewDto newReviewDto) {
        try {
            ReviewDto review = reviewService.createReview(newReviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable(value = "id") Long id) {
        try {
            reviewService.deleteReviewById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> updateReview(@PathVariable(value = "id") Long id, @RequestBody UpdateReviewDto updateReviewDto) {
        try {
            ReviewDto reviewDto = reviewService.updateReview(id, updateReviewDto);
            return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

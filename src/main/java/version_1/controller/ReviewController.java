package version_1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import version_1.dto.ReviewDto;
import version_1.model.Review;
import version_1.repository.ReviewRepository;
import version_1.service.ReviewService;
import java.util.List;

@RestController
@RequestMapping("/papalapa/reviews")
@Tag(name = "Review")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    private final ModelMapper modelMapper = new ModelMapper();

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getReviews() {
        List<Review> reviewList = reviewService.getReviews();
        List<ReviewDto> reviewDtoList = reviewList
                .stream()
                .map(review -> new ReviewDto(
                        review.getId(),
                        review.getContent(),
                        review.getReviewerName(),
                        review.getRating(),
                        review.getImageUrl(),
                        review.getProduct().getCategory().getTitle()))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(reviewDtoList);
    }
}

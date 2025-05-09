package version_1.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.OptimisticLock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import version_1.dto.PagingDTO;
import version_1.dto.WBResponseDtos.WBFeedbackDto;
import version_1.dto.WBResponseDtos.WBReviewResponseDto;
import version_1.dto.review.NewReviewDto;
import version_1.dto.review.ReviewDto;
import version_1.model.Product;
import version_1.model.Review;
import version_1.providers.WBProvider;
import version_1.repository.ProductRepository;
import version_1.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
public class ReviewService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    WBProvider wbProvider;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<Review> getReviewsFromWb() {
        WBReviewResponseDto wbReview = wbProvider.getReviewList(true, 150);
        List<WBFeedbackDto> feedbackDtoList = wbReview.getData().getFeedbacks()
                .stream()
                .filter(feedback -> feedback.getProductValuation() == 5)
                .filter(feedback -> feedback.getPros() != null && !feedback.getPros().isEmpty())
                .filter(feedback -> feedback.getPhotoLinks() != null && !feedback.getPhotoLinks().isEmpty())
                .toList();
        List<Review> reviewList = new ArrayList<>();
        feedbackDtoList.forEach(feedback -> {
            Review review = new Review();
            review.setReviewerName(feedback.getUserName());
            review.setContent(feedback.getPros());
            review.setProduct(productRepository.findProductByTitle(feedback.getProductDetails().getSupplierArticle()));
            if (!feedback.getPhotoLinks().isEmpty()) review.setImageUrl(feedback.getPhotoLinks().get(0).getMiniSize());
            review.setRating(feedback.getProductValuation());
            reviewList.add(review);
        });
        return reviewList;
    }

    public PagingDTO<ReviewDto> getReviewsFromDb(int page, int limit) {
        Page<Review> reviewList = reviewRepository.findAll(
                PageRequest.of(page - 1, limit, Sort.by("createdAt").descending()));
        PagingDTO<ReviewDto> reviewPagingDTO = new PagingDTO<>();
        reviewPagingDTO.setData(reviewList
                .stream()
                .map(review -> {
                    ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
                    reviewDto.setProductName(review.getProduct().getCategory().getTitle());
                    return reviewDto;
                })
                .toList());
        reviewPagingDTO.setPages(reviewList.getTotalPages());
        reviewPagingDTO.setTotal(Math.toIntExact(reviewList.getTotalElements()));
        reviewPagingDTO.setPage(page);
        reviewPagingDTO.setLimit(limit);
        return reviewPagingDTO;
    }

    public ReviewDto findReviewById(Long id) {
        try {
            Review review = reviewRepository.findById(id).orElseThrow();
            ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
            reviewDto.setProductName(review.getProduct().getCategory().getTitle());
            return reviewDto;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Отзыв с id=" + id + " не найден");
        }
    }

    @Transactional
    public ReviewDto createReview(NewReviewDto newReview) {
        try {
            log.info(newReview);
            Product product = productRepository.findById(Long.valueOf(newReview.getItemId())).orElseThrow();
            log.info(product.getId());
            Review review = modelMapper.map(newReview, Review.class);
            review.setProduct(product);
//            return reviewRepository.save(review);
            Review savedReview = reviewRepository.saveReview(review);
            return findReviewById(savedReview.getId());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteReviewById(Integer id) {
        try {
            reviewRepository.findById(Long.valueOf(id)).orElseThrow();
            reviewRepository.deleteById(Long.valueOf(id));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Отзыв с id=" + id + " не найден");
        }
    }
}

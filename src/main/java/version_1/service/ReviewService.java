package version_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import version_1.dto.WBResponseDtos.WBFeedbackDto;
import version_1.dto.WBResponseDtos.WBReviewResponseDto;
import version_1.model.Review;
import version_1.providers.WBProvider;
import version_1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WBProvider wbProvider;

    public List<Review> getReviews() {
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
}

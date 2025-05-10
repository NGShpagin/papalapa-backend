package version_1.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import version_1.dto.review.NewReviewDto;
import version_1.model.Review;

import java.time.LocalDateTime;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {
    @Override
    @NotNull
    Page<Review> findAll(@NotNull Pageable pageable);

    @Query(value = "INSERT INTO review (content, reviewer_name, rating, image_url, product_id, created_at) " +
            "VALUES (:content, :reviewerName, :rating, :imageUrl, :productId, :createdAt) returning *", nativeQuery = true)
    Review saveReview(@Param("content") String content,
                      @Param("reviewerName") String reviewerName,
                      @Param("rating") int rating,
                      @Param("imageUrl") String imageUrl,
                      @Param("productId") Long productId,
                      @Param("createdAt") LocalDateTime createdAt);
}

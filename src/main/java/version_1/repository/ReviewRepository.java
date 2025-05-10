package version_1.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import version_1.dto.review.NewReviewDto;
import version_1.model.Review;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {
    @Override
    @NotNull
    Page<Review> findAll(@NotNull Pageable pageable);

    @Query(value = "INSERT INTO review (content, reviewer_name, rating, image_url, product_id, created_at) " +
            "VALUES (:#{#entity.getContent()}, " +
            ":#{#entity.getReviewerName()}, " +
            ":#{#entity.getRating()}, " +
            ":#{#entity.getImageUrl()}, " +
            ":#{#entity.getProduct().getId()}, " +
            ":#{#entity.getCreatedAt()}) returning *", nativeQuery = true)
    Review saveReview(Review entity);
}

package version_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import version_1.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {
}

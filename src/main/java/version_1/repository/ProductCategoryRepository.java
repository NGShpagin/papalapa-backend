package version_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import version_1.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, PagingAndSortingRepository<ProductCategory, Long> {
    ProductCategory findByTitle(String title);
}

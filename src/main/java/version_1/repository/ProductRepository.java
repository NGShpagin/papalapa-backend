package version_1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import version_1.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Query(value = "SELECT * FROM products p WHERE p.category_id = :categoryId", nativeQuery = true)
    Page<Product> findProductsByTypeId(Pageable pageable, long categoryId);

    Product findProductByTitle(String title);
}

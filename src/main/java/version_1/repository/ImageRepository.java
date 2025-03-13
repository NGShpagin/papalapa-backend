package version_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import version_1.model.Image;
import version_1.model.ImageType;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByType(ImageType type);

    Image findByType(ImageType type);
}

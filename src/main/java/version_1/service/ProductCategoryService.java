package version_1.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import version_1.dto.NewProductCategoryDto;
import version_1.model.ProductCategory;
import version_1.repository.ProductCategoryRepository;
import java.time.LocalDateTime;

@Log4j2
@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public ProductCategory addNew(NewProductCategoryDto newProductCategoryDto) {
        try {
            ProductCategory productCategory = productCategoryRepository.findByTitle(newProductCategoryDto.getTitle());
            if (productCategory != null) throw new RuntimeException("Категория с указанным наименованием изделия уже существует");
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (newProductCategoryDto.getTitle() == null || newProductCategoryDto.getTitle().isEmpty()) throw new RuntimeException("title не может быть пустым");
        ProductCategory productCategory = modelMapper.map(newProductCategoryDto, ProductCategory.class);
        productCategory.setCreatedAt(LocalDateTime.now());
        return productCategoryRepository.save(productCategory);
    }
}

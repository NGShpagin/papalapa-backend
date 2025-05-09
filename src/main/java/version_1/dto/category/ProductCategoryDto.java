package version_1.dto.category;

import lombok.Data;
import version_1.dto.product.ProductShortInfoDto;

import java.util.List;

@Data
public class ProductCategoryDto {
    private Long id;
    private String title;
    private List<ProductShortInfoDto> colorList;
}

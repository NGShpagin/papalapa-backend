package version_1.dto.product;

import lombok.Data;
import version_1.dto.ProductImageDto;

import java.util.List;

@Data
public class ProductShortInfoDto {
    private Long id;
    private String title;
    private Long nmId;
    private String colorName;
    private String colorValue;
    private String wbUrl;
    private Double price;
    private List<ProductImageDto> imageList;
}

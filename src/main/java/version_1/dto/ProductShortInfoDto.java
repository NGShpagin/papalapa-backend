package version_1.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductShortInfoDto {
    private Long id;
    private String title;
    private String colorName;
    private String colorValue;
    private String wbUrl;
    private Double price;
    private List<ProductImageDto> imageList;
}

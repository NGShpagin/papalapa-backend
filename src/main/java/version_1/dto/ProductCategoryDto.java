package version_1.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryDto {
    private Long id;
    private String title;
    private List<ProductShortInfoDto> colorList;
}

package version_1.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    private String colorName;
    private String colorValue;
    private String image;
    private String wbUrl;
    private Double price;
    private Integer quantity;
    private String composition;
    private String size;
//    private ProductCategoryShortDto category;
}

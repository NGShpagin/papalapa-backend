package version_1.dto;

import lombok.Data;

@Data
public class ReviewProductDto {
    private Long id;
    private String title;
    private String colorName;
    private String colorValue;
    private Double price;
    private ProductCategoryShortDto productCategory;
}

package version_1.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import version_1.dto.category.ProductCategoryDto;

@Data
public class NewProductDto {
    private String title;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    private String colorName;
    private String colorValue;
    private String image;
    private Double price;
    private String wbUrl;
    private Integer quantity;
    private String composition;
    private String size;
    @NotNull
    private Integer categoryId;
}

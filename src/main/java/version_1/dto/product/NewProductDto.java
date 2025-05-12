package version_1.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import version_1.dto.category.ProductCategoryDto;

@Data
public class NewProductDto {
    @NotEmpty(message = "title является обязательным параметром")
    private String title;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    @NotEmpty(message = "colorName является обязательным параметром")
    private String colorName;
    @NotEmpty(message = "colorValue является обязательным параметром")
    private String colorValue;
    private String image;
    @NotEmpty(message = "price является обязательным параметром")
    private Double price;
    private String wbUrl;
    @NotEmpty(message = "quantity является обязательным параметром")
    private Integer quantity;
    private String composition;
    private String size;
    @NotNull(message = "categoryId является обязательным параметром")
    private Integer categoryId;
}

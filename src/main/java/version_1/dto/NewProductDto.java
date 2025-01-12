package version_1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private ProductCategoryDto category;
}

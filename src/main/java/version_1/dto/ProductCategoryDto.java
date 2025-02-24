package version_1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import version_1.model.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {
    private Long id;
    private String title;
    private List<ProductShortInfoDto> colorList;
}

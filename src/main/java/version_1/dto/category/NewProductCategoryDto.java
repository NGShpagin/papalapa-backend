package version_1.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewProductCategoryDto {
    @NotNull
    private String title;
}

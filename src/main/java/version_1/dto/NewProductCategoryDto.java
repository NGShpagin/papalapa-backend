package version_1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewProductCategoryDto {
    @NotNull
    private String title;
}

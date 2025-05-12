package version_1.dto.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewProductCategoryDto {
    @NotEmpty(message = "title является обязательным параметром")
    private String title;
}

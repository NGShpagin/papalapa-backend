package version_1.dto.review;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReviewDto {
    @NotEmpty(message = "content является обязательным параметром")
    private String content;
    @NotEmpty(message = "reviewerName является обязательным параметром")
    private String reviewerName;
    @NotNull(message = "rating является обязательным параметром")
    @Min(value = 1, message = "Рейтинг должен быть больше или равен 1 и меньше или равен 5")
    @Max(value = 5, message = "Рейтинг должен быть меньше или равен 5 и больше или равен 1")
    private Integer rating;
    private String imageUrl;
    @NotNull(message = "productId является обязательным параметром")
    private Integer productId;
}

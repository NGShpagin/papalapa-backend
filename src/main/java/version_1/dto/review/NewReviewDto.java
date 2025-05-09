package version_1.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReviewDto {
    private String content;
    @NotNull
    private String reviewerName;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    private String imageUrl;
    private Integer itemId;
}

package version_1.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private String content;
    private String reviewerName;
    @Min(1)
    @Max(5)
    private Integer rating;
    private String imageUrl;
    private String productName;
    private LocalDateTime createdAt;
}

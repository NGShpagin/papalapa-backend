package version_1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import version_1.dto.WBResponseDtos.WBProductDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String content;
    private String reviewerName;
    private Integer rating;
    private String imageUrl;
    private String itemName;
}

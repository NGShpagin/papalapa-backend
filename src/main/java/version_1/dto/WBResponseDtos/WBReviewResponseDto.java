package version_1.dto;

import lombok.Data;

import java.util.List;

@Data
public class WBReviewResponseDto {

    private Integer countUnanswered;
    private Integer countArchive;
    private List<FeedbackDto> feedbacks;
}

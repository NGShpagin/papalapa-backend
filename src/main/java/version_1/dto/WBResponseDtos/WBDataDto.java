package version_1.dto.WBResponseDtos;

import lombok.Data;

import java.util.List;

@Data
public class WBDataDto {
    private Integer countUnanswered;
    private Integer countArchive;
    private List<WBFeedbackDto> feedbacks;
}

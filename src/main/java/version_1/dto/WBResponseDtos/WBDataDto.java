package version_1.dto.WBResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WBDataDto {
    private Integer countUnanswered;
    private Integer countArchive;
    private List<WBFeedbackDto> feedbacks;
}

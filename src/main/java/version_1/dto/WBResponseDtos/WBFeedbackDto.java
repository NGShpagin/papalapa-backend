package version_1.dto.WBResponseDtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WBFeedbackDto {
    private String id; // ID отзыва
    private String text; // Текст отзыва
    private String pros; // Достоинства товара
    private String cons; // Недостатки товара
    private Integer productValuation; // Оценка товара
    private LocalDateTime createdDate; // Дата и время создания отзыва
    private String state; // Статус отзыва: none - не обработан (новый), wbRu - обработан
    private Boolean wasViewed; // Просмотрен ли отзыв
    private String userName; // Имя автора отзыва
    private String subjectName; // Название предмета
    private WBProductDto productDetails;
    private List<WBPhotoDto> photoLinks;
}

package version_1.dto.WBResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WBPhotoDto {
    private String fullSize; // Адрес фотографии полного размера
    private String miniSize; // Адрес фотографии маленького размера
}

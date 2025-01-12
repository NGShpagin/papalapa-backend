package version_1.dto.WBResponseDtos;

import lombok.Data;

@Data
public class PhotoDto {
    private String fullSize; // Адрес фотографии полного размера
    private String miniSize; // Адрес фотографии маленького размера
}

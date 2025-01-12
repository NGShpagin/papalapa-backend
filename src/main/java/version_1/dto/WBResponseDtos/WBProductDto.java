package version_1.dto.WBResponseDtos;

import lombok.Data;

@Data
public class WBProductDto {
    private Integer nmId; // Артикул WB
    private Integer imtId; // ID карточки товара
    private String productName; // Название товара
    private String supplierArticle; // Артикул продавца
}

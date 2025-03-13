package version_1.dto.WBResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WBItemDto {
    // Артикул WB
    Integer nmID;
    // Артикул продавца
    String vendorCode;
    // Размер
    List<WBItemSize> sizes;
    // Валюта, по стандарту ISO 4217
    String currencyIsoCode4217;
    // Скидка, %
    Integer discount;
    // Скидка WB Клуба, %
    Integer clubDiscount;
    // Можно ли устанавливать цены отдельно для разных размеров: true — можно, false — нельзя. Эта возможность зависит от категории товара
    boolean editableSizePrice;
}

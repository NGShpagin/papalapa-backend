package version_1.dto.WBResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WBItemSize {
    Integer sizeId;
    Integer price;
    Float discountedPrice;
    Float clubDiscountedPrice;
    String techSizeName;
}

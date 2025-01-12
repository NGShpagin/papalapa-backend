package version_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductShortInfoDto {
    private Long id;
    private String title;
    private String colorName;
    private String colorValue;
    private String wbUrl;
    private Double price;
    private List<ProductImageDto> imageList;
}

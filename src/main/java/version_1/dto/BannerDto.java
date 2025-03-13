package version_1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import version_1.model.ImageType;

@Data
public class BannerDto {
    private Long id;
    @NotNull
    private String url;
    @NotNull
    private ImageType type;
    private Integer position;
}

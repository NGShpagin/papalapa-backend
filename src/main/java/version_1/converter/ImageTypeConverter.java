package version_1.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import version_1.model.ImageType;

import java.util.stream.Stream;

@Converter
public class ImageTypeConverter implements AttributeConverter<ImageType, String> {

    @Override
    public String convertToDatabaseColumn(ImageType imageType) {
        if (imageType == null) {
            return null;
        }
        return imageType.getTitle();
    }

    @Override
    public ImageType convertToEntityAttribute(String title) {
        if (title == null) {
            return null;
        }

        return Stream.of(ImageType.values())
                .filter(c -> c.getTitle().equals(title))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

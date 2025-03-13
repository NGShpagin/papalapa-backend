package version_1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {
    CAROUSEL_BANNER("CAROUSEL_BANNER"),
    SELECT_BANNER("SELECT_BANNER"),
    SELECT_CARD("SELECT_CARD"),
    ABOUT_BANNER("ABOUT_BANNER"),
    COMMON_BANNER("COMMON_BANNER");

    private final String title;
}

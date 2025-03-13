package version_1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import version_1.dto.BannerDto;
import version_1.model.Image;
import version_1.model.ImageType;
import version_1.repository.ImageRepository;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/papalapa/images")
@Tag(name = "Image")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getImageByType(@RequestParam ImageType imageType) {
        if (imageType == ImageType.CAROUSEL_BANNER) {
            List<Image> carouselBanners = imageRepository.findAllByType(imageType);
            return ResponseEntity.status(HttpStatus.OK).body(carouselBanners.stream().map(banner -> modelMapper.map(banner, BannerDto.class)).toList());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(imageRepository.findByType(imageType), BannerDto.class));
        }
    }

    @CrossOrigin
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getImageById(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageRepository.findById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}

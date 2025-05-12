package version_1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import version_1.dto.category.NewProductCategoryDto;
import version_1.dto.category.ProductCategoryDto;
import version_1.dto.ResponseMessageDto;
import version_1.model.ProductCategory;
import version_1.repository.ProductCategoryRepository;
import version_1.service.ProductCategoryService;

import java.util.NoSuchElementException;

@Log4j2
@RestController
@RequestMapping("/papalapa/categories")
@Tag(name = "ProductCategory")
public class ProductCategoryController {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductCategoryService productCategoryService;

    private final ModelMapper modelMapper = new ModelMapper();

//    @CrossOrigin
//    @GetMapping
//    public ResponseEntity<?> getAllCategories() {
//        return ResponseEntity.status(HttpStatus.OK).body(productCategoryService.getAllCategories()
//                .stream()
//                .map(category -> modelMapper.map(category, ProductCategoryDto.class)).toList());
//    }

    @PostMapping
    public ResponseEntity<?> addNewProductCategory(@Valid @RequestBody NewProductCategoryDto newProductCategoryDto) {
        try {
            ProductCategory productCategory = productCategoryService.addNew(newProductCategoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(productCategory, ProductCategoryDto.class));
         } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable(value = "id") Long id) {
        try {
            ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(productCategory, ProductCategoryDto.class));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable(value = "id") Long id) {
        try {
            productCategoryRepository.findById(id).orElseThrow();
            productCategoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new ResponseMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

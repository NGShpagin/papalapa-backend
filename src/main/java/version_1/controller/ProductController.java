package version_1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import version_1.dto.NewProductDto;
import version_1.dto.ProductDto;
import version_1.dto.ProductShortInfoDto;
import version_1.model.Product;
import version_1.repository.ProductRepository;
import version_1.service.ProductService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/papalapa/products")
@Tag(name = "Product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Получить товар по его Идентификатору
     *
     * @param id - идентификатор товара
     * @return полученный товар
     */
    @Operation(summary = "Get product by its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "204", description = "Product not found",
                    content = @Content)})
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(product, ProductDto.class));
        else return new ResponseEntity<>(
                new ResponseMessage(HttpStatus.NOT_FOUND.value(), "Изделие с id = " + id + "не найдено"),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Получить все товары постранично
     *
     * @param categoryId - тип товара
     * @return список товаров
     */
    @CrossOrigin
    @Operation(summary = "Get products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<ProductShortInfoDto>> getAllProducts(@RequestParam(required = false) String categoryId,
                                                                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                    @RequestParam(required = false, defaultValue = "5") int pageSize) {
        List<Product> products;
        if (categoryId == null)
            products = productRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        else
            products = productRepository.findProductsByTypeId(
                            PageRequest.of(pageNumber, pageSize),
                            Long.parseLong(categoryId))
                    .getContent();
        List<ProductShortInfoDto> productList = products.stream().map(product -> modelMapper.map(product, ProductShortInfoDto.class)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @Operation(summary = "Add new product")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody NewProductDto newProductDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(productService.create(newProductDto), ProductDto.class));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update product entire", description = "Обновить изделие целиком")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateProductEntire(@PathVariable long id, @RequestBody ProductDto product) {
        try {
            product.setId(id);
            Product updatedProduct = productService.updateEntire(product);
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(updatedProduct, ProductDto.class));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessage(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update product partially", description = "Обновить изделие частично")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> updateProductPartially(@PathVariable long id, @RequestBody ProductDto product) {
        try {
            product.setId(id);
            Product updatedProduct = productService.updatePartially(product);
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(updatedProduct, ProductDto.class));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new ResponseMessage(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get products with price", description = "Получить карточки товара с ценами")
    @GetMapping(path = "/wb-items")
    @CrossOrigin
    public ResponseEntity<?> getAllProductsWithPrice(@RequestParam(value = "filterNmID") Integer filterNmID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getWbItemByNmId(filterNmID));
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.TooManyRequests e) {
            return new ResponseEntity<>(
                    new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

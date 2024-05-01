package version_1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import version_1.model.Product;
import version_1.repository.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    /**
     * Получить товар по его Идентификатору
     * @param id - идентификатор товара
     * @return полученный товар
     */
    @Operation(summary = "Get product by its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    @GetMapping(path = "/{id}")
    public Product getProduct(@PathVariable long id) {
        return productRepository.findById(id).orElseThrow();
    }

    /**
     * Получить все товары
     * @param typeId - тип товара
     * @return список товаров
     */
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String typeId) {
        if (typeId == null) return productRepository.findAll();
        return productRepository.getProductsByTypeId(Long.parseLong(typeId));
    }
}

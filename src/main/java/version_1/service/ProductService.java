package version_1.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import version_1.dto.NewProductDto;
import version_1.dto.ProductDto;
import version_1.dto.WBResponseDtos.WBGoodsResponseDto;
import version_1.dto.WBResponseDtos.WBItemDto;
import version_1.model.Product;
import version_1.model.ProductCategory;
import version_1.providers.WBProvider;
import version_1.repository.ProductCategoryRepository;
import version_1.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Log4j2
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WBProvider wbProvider;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Product updateEntire(ProductDto product) {
        Product changedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new NoSuchElementException("Изделие с id = " + product.getId() + "не найдено"));
        changedProduct.setChangedAt(LocalDateTime.now());
//        changedProduct.setTitle(product.getTitle());
//        changedProduct.setQuantity(product.getQuantity());
//        changedProduct.setCategory(modelMapper.map(product.getCategory(), ProductCategory.class));
        changedProduct.setColorName(product.getColorName());
        changedProduct.setColorValue(product.getColorValue());
//        changedProduct.setSize(product.getSize());
//        changedProduct.setComposition(product.getComposition());
        return productRepository.save(changedProduct);
    }

    @Transactional
    public Product updatePartially(ProductDto product) {
        Product changedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new NoSuchElementException("Изделие с id = " + product.getId() + "не найдено"));
        changedProduct.setChangedAt(LocalDateTime.now());
//        if (product.getTitle() != null) changedProduct.setTitle(product.getTitle());
//        if (product.getDescription() != null) changedProduct.setDescription(product.getDescription());
        if (product.getPrice() != null) changedProduct.setPrice(product.getPrice());
//        if (product.getQuantity() != null) changedProduct.setQuantity(product.getQuantity());
//        if (product.getCategory() != null) changedProduct.setCategory(modelMapper.map(product.getCategory(), ProductCategory.class));
//        if (product.getColor() != null) changedProduct.setColor(product.getColor());
//        if (product.getSize() != null) changedProduct.setSize(product.getSize());
//        if (product.getComposition() != null) changedProduct.setComposition(product.getComposition());
//        if (product.getMinAge() != null) changedProduct.setMinAge(product.getMinAge());
//        if (product.getMaxAge() != null) changedProduct.setMaxAge(product.getMaxAge());
        return productRepository.save(changedProduct);
    }

    public Product create(NewProductDto newProductDto) {
        if (newProductDto.getTitle() == null || newProductDto.getTitle().isEmpty()) throw new RuntimeException("title не может быть пустым");
        if (newProductDto.getColorName() == null || newProductDto.getColorName().isEmpty()) throw new RuntimeException("colorName не может быть пустым");
        if (newProductDto.getColorValue() == null || newProductDto.getColorValue().isEmpty()) throw new RuntimeException("colorValue не может быть пустым");
        try {
            ProductCategory productCategory = productCategoryRepository.findById(newProductDto.getCategory().getId()).orElseThrow();
            Product product = modelMapper.map(newProductDto, Product.class);
            product.setCreatedAt(LocalDateTime.now());
            product.setCategory(productCategory);
            return productRepository.save(product);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Указанная категория не найдена");
        }
    }

    public WBItemDto getWbItemByNmId(int filterNmId) {
        try {
            WBGoodsResponseDto wbGoodsResponseDto = wbProvider.getItemWithPriceByNmId(filterNmId);
            return wbGoodsResponseDto.getData().getListGoods().get(0);
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.TooManyRequests e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getStatusText());
        }
    }

    public List<ProductCategory> getAllProducts() {
        try {
            List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
            WBGoodsResponseDto wbGoodsResponseDto = wbProvider.getAllItemsWithPrice();
            List<WBItemDto> wbItemDtoList = wbGoodsResponseDto.getData().getListGoods();
            for (ProductCategory productCategory : productCategoryList) {
                for (Product color : productCategory.getColorList()) {
                    boolean isFound = false;
                    for (WBItemDto wbItemDto : wbItemDtoList) {
                        if (isFound) {
                            log.info(isFound);
                            break;
                        }
                        else if (Objects.equals(wbItemDto.getVendorCode(), color.getTitle())) {
                            color.setPrice(wbItemDto.getSizes().get(0).getDiscountedPrice());
                            isFound = true;
                        }
                    }
                }
            }
            return productCategoryList;
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.TooManyRequests e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getStatusText());
        }
    }

}

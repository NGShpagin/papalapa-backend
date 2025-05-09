package version_1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Артикул WB
     */
    @Column(name = "nm_id", nullable = true)
    private Long nmId;

    /**
     * Описание
     */
    @Column(name = "description")
    private String description;

    /**
     * От какого возраст можно использовать изделие
     */
    @Min(0)
    @Column(name = "min_age")
    private Integer minAge;

    /**
     * До какого возраста можно использовать изделие
     */
    @Min(1)
    @Column(name = "max_age")
    private Integer maxAge;

    /**
     * Наименование цвета
     */
    @Column(name = "color_name")
    private String colorName;

    /**
     * Обозначение цвета в формате HEX
     */
    @Column(name = "color_value")
    private String colorValue;

    /**
     * Стоимость изделия
     */
    @Column(name = "price")
    private Double price;

    /**
     * Url-адрес изделия на wb
     */
    @Column(name = "wb_url", length = 1000)
    private String wbUrl;

    /**
     * Количество / Остаток
     */
    @Column(name = "quantity", columnDefinition = "Количество")
    private Integer quantity;

    /**
     * Состав изделия
     */
    @Column(name = "composition")
    private String composition;

    /**
     * Размер изделия
     */
    @Column(name = "size")
    private String size;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "changed_at")
    private LocalDateTime changedAt = null;

    /**
     * Категория изделия
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> imageList;
}

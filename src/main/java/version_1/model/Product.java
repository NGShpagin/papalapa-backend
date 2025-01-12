package version_1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * Описание
     */
    @Column(name = "description")
    private String description;

    /**
     * От какого возраст можно использовать изделие
     */
    @Column(name = "min_age")
    private Integer minAge;

    /**
     * До какого возраста можно использовать изделие
     */
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
     * url-адрес изделия на wb
     */
    @Column(name = "wb_url", length = 1000)
    private String wbUrl;

    /**
     * Количество / Остаток
     */
    @Column(name = "quantity")
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
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> imageList;
}

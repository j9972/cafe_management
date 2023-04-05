package cafe.cafe_management_system.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWrapper {
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private String status;

    private Long categoryId;

    private String categoryName;

    public ProductWrapper(Long id, String name, String description, Integer price, String status, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public ProductWrapper(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Long id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}

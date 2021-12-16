package ru.learnup.javaqa.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithoutTitle {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("price")
    private Object price;
    @JsonProperty("categoryTitle")
    private String categoryTitle;

    public ProductWithoutTitle(Product product) {
        this.id = product.getId();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategoryTitle();
    }
}

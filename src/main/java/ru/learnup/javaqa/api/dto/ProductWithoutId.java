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
public class ProductWithoutId {

    @JsonProperty("title")
    private Object title;
    @JsonProperty("price")
    private Object price;
    @JsonProperty("categoryTitle")
    private String categoryTitle;

    public ProductWithoutId(Product product) {
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategoryTitle();
    }
}

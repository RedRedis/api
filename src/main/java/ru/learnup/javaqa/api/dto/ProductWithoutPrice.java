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
public class ProductWithoutPrice {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private Object title;
    @JsonProperty("categoryTitle")
    private String categoryTitle;

    public ProductWithoutPrice(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.categoryTitle = product.getCategoryTitle();
    }
}

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
public class CategoryTitle {

    @JsonProperty("categoryTitle")
    private String categoryTitle;

    public CategoryTitle(Product product) {
        this.categoryTitle = product.getCategoryTitle();
    }
}

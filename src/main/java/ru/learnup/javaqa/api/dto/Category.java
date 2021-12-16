package ru.learnup.javaqa.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Category {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("products")
    private List<Product> products;
    @JsonProperty("title")
    private String title;

    public Category() {
    }

}

package ru.learnup.javaqa.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private Object title;
    @JsonProperty("price")
    private Object price;
    @JsonProperty("categoryTitle")
    private String categoryTitle;

}

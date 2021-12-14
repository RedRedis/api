package ru.learnup.javaqa.api.dto;

import lombok.*;

@Data
@Builder
public class Product {

    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"title\":\"" + title + '\"' +
                ", \"price\":" + price +
                ", \"categoryTitle\":\"" + categoryTitle + '\"' +
                '}';
    }

    public String toStringWithoutId() {
        return "{" +
                "\"title\":\"" + title + '\"' +
                ", \"price\":" + price +
                ", \"categoryTitle\":\"" + categoryTitle + '\"' +
                '}';
    }

}

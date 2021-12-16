package ru.learnup.javaqa.api.dto;

import lombok.*;

@Data
@Builder
public class Product {

    private Integer id;
    private Object title;
    private Object price;
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

    public String toStringWithoutPrice() {
        return "{" +
                "\"title\":\"" + title + '\"' +
                ", \"categoryTitle\":\"" + categoryTitle + '\"' +
                '}';
    }

    public String toStringWithoutTitle() {
        return "{" +
                "\"price\":" + price +
                ", \"categoryTitle\":\"" + categoryTitle + '\"' +
                '}';
    }

    public String toStringOnlyCategoryTitle() {
        return "{" +
                "\"categoryTitle\":\"" + categoryTitle + '\"' +
                '}';
    }

}

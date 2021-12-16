package ru.learnup.javaqa.api.enums;

import lombok.Getter;

@Getter
public enum CategoryType {

    FOOD(1, "Food"),
    ELECTRONIC(2, "Electronic"),
    FURNITURE(3, "Furniture");

    private int id;
    private String name;

    CategoryType( int id,  String name) {
        this.id = id;
        this.name = name;
    }
}
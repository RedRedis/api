package ru.learnup.javaqa.api.enums;

import lombok.Getter;

@Getter
public enum SentProductType {
    FULL,
    WITHOUT_ID,
    WITHOUT_PRICE,
    WITHOUT_TITLE,
    ONLY_CATEGORY_TITLE;
}

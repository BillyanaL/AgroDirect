package com.example.agrodirect.models.enums;

public enum OrderStatus {

    PENDING,
    ACCEPTED,
    SHIPPED,
    DELIVERED,
    CANCELLED ;

    public String getDisplayName() {
        return switch (this) {
            case PENDING -> "Обработва се";
            case ACCEPTED -> "Приета";
            case SHIPPED -> "Изпратена";
            case DELIVERED -> "Доставена";
            case CANCELLED -> "Отказана";
        };
    }

}

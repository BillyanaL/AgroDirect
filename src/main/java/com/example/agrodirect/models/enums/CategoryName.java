package com.example.agrodirect.models.enums;

public enum CategoryName {

    VEGETABLES,
    FRUITS,
    DAIRY,
    MEAT,
    EGGS,
    GRAINS,
    HONEY,
    HERBS,
    JAMS;


    public String getDisplayName() {
        return switch (this) {
            case VEGETABLES -> "🥦 Зеленчуци";
            case FRUITS -> "🍎 Плодове";
            case DAIRY -> "🧀 Млечни";
            case MEAT -> "🥩 Месо";
            case EGGS -> "🥚 Яйца";
            case GRAINS -> "🌾 Зърнени";
            case HONEY -> "🍯 Мед";
            case HERBS -> "🌿 Билки";
            case JAMS -> "🍓 Сладка";
        };
    }
}

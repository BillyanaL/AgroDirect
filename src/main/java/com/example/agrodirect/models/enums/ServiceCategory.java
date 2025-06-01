package com.example.agrodirect.models.enums;

public enum ServiceCategory {

    FIELD_WORK,
    MACHINERY_RENTAL,
    CONSULTING,
    EDUCATION,
    FARM_VISIT,
    BEEKEEPING,
    ORGANIC_SUPPORT,
    OTHER;

    public String getDisplayName() {
        return switch (this) {
            case FIELD_WORK -> "🚜 Земеделски услуги (оране, сеитба, пръскане)";
            case MACHINERY_RENTAL -> "🔧 Наем на техника";
            case CONSULTING -> "📋 Консултации";
            case EDUCATION -> "📚 Обучения и уъркшопи";
            case FARM_VISIT -> "🏡 Посещение на ферма";
            case BEEKEEPING -> "🐝 Пчеларски услуги";
            case ORGANIC_SUPPORT -> "♻️ Био торове и компост";
            case OTHER -> "❓ Друго";
        };
    }
}

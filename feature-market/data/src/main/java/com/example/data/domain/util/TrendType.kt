package com.example.data.domain.util

enum class TrendType {
    UP,
    DOWN,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): TrendType {
            return when (value?.lowercase()) {
                "up" -> UP
                "down" -> DOWN
                else -> UNKNOWN
            }
        }
    }
}

package ru.netology.cookbook

import kotlinx.serialization.Serializable

@Serializable
enum class Category(
    val key: String,
    val categoryId: Int
) {
    European("European", 1),
    Russian("Russian", 2),
    Asian("Asian", 3),
    PanAsian("Pan-Asian", 4),
    Oriental("Oriental", 5),
    American("American", 6),
    Mediterranean("Mediterranean", 7)

}

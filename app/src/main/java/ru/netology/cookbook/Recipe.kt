package ru.netology.cookbook

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Long,
    val name: String,
    val author: String,
    val components: String,
    var cooking: List<Steps>,
    val picture: String = "",
    val category: Category,
    val isLiked: Boolean = false,
)
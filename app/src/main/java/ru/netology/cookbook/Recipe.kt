package ru.netology.cookbook

import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
    val id : Int,
    val name : String,
    val author : String,
    val components: String,
    val cooking: String,
    val picture: String="",
    val category: Category,
    val isLiked: Boolean= false
        )
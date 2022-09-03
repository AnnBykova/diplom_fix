package ru.netology.cookbook

import kotlinx.serialization.Serializable

@Serializable
data class Steps (
    val stepId : Long,
    val stepOrder : Int,
    val stepText : String,
    var recipeId : Long,
    val stepPicture: String="no"
)
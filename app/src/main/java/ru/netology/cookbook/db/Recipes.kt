package ru.netology.cookbook.db

import androidx.room.TypeConverter
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps

fun RecipeEntity.toModel() = Recipe(
    id = id,
    name = name,
    author = author,
    components = components,
    cooking = listOf(Steps (0L, 1,"",0L)),
    picture = picture,
    category = category,
    isLiked = isLiked
)

fun Recipe.toEntity() = ru.netology.cookbook.db.RecipeEntity(
    id = id,
    name = name,
    author = author,
    components = components,
    picture = picture,
    category = category,
    isLiked = isLiked
)

fun StepsEntity.toModel() = Steps(
    stepId = stepId,
    stepOrder = stepOrder,
    stepText = stepText,
    recipeId = recipeId,
    stepPicture = stepPicture
)

fun Steps.toEntity() = ru.netology.cookbook.db.StepsEntity(
    stepId = stepId,
    stepOrder = stepOrder,
    stepText = stepText,
    recipeId = recipeId,
    stepPicture = stepPicture

)

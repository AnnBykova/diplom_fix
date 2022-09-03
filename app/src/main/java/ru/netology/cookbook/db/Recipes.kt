package ru.netology.cookbook.db

import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps

fun RecipeEntity.toModel() = Recipe(
    id = id,
    name = name,
    author = author,
    components = components,
    cooking = cooking,
    picture = picture,
    category = category,
    isLiked = isLiked
)

fun Recipe.toEntity() = ru.netology.cookbook.db.RecipeEntity(
    id = id,
    name = name,
    author = author,
    components = components,
    cooking = cooking,
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